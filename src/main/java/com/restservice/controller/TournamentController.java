package com.restservice.controller;

import com.restservice.model.Match;
import com.restservice.model.dto.MatchDto;
import com.restservice.service.TournamentService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Controller
public class TournamentController {
    private final TournamentService tournamentService;

    @GetMapping("list-by-customer-id/{id}")
    public ResponseEntity<Set<MatchDto>> listForCustomer(@PathVariable Long id, @RequestParam(required = false) String summaryType) {
        if (isNull(id)) {
            return ResponseEntity.badRequest().build();
        }
        
        val matches = tournamentService.listAllByCustomerId(id);
        if (hasText(summaryType)) {
            matches.forEach(match -> match.setSummaryByType(summaryType));
        }
        return ResponseEntity.ok(matches.stream().map(Match::dto).collect(toSet()));
    }

    @GetMapping("list-by-licence/{licence}")
    public ResponseEntity<Set<MatchDto>> listByLicence(@PathVariable String licence, @RequestParam(required = false) String summaryType) {
        if (isNull(licence)) {
            return ResponseEntity.badRequest().build();
        }

        val matches = tournamentService.listAllByLicence(licence);
        if (hasText(summaryType)) {
            matches.forEach(match -> match.setSummaryByType(summaryType));
        }
        return ResponseEntity.ok(matches.stream().map(Match::dto).collect(toSet()));
    }

    @GetMapping("list-all-by-licences")
    public ResponseEntity<Set<MatchDto>> listAllByLicences(@RequestParam Set<String> licences, @RequestParam(required = false) String summaryType) {
        if (isNull(licences) || licences.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        val matches = tournamentService.listAllForLicences(licences);
        if (hasText(summaryType)) {
            matches.forEach(match -> match.setSummaryByType(summaryType));
        }
        return ResponseEntity.ok(matches.stream().map(Match::dto).collect(toSet()));
    }
    
}
