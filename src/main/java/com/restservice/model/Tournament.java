package com.restservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
@Builder
public class Tournament {
    private Long tournamentId;
    private String licence;
    private Set<Match> matches;
}
