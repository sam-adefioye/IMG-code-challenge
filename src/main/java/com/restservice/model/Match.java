package com.restservice.model;

import com.restservice.model.dto.MatchDto;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private Long matchId;
    private Date startDate;
    private String playerA;
    private String playerB;
    private String summary;

    public Date getStartDate() {
        return isNull(startDate) ? null : (Date) startDate.clone();
    }

    public void setStartDate(Date date) {
        startDate = (Date) date.clone();
    }

    public void setSummaryByType(String type){
        if (hasText(type)) {
            if (type.equals("AvB")) {
                summary = format("%s vs %s", playerA, playerB);
            } else if (type.equals("AvBTime")) {
                val now = LocalDateTime.now();
                val matchTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                val minutes = MINUTES.between(now, matchTime);
                val future = format("%s vs %s, starts in %s minutes.", playerA, playerB, minutes);
                val past = format("%s vs %s, started %s minutes ago.", playerA, playerB, -minutes);
                summary = now.isBefore(matchTime) ? future : past;
            }
        }
    }

    public MatchDto dto() {
        return MatchDto.builder()
                .matchId(matchId)
                .playerA(playerA)
                .playerB(playerB)
                .startDate(format("%tF %<tT", startDate))
                .summary(hasText(summary) ? summary : "")
                .build();
    }
}
