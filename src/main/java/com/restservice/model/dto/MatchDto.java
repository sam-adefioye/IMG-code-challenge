package com.restservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
    private Long matchId;
    private String startDate;
    private String playerA;
    private String playerB;
    private String summary;
}
