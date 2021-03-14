package com.restservice.controller;

import com.restservice.model.Match;
import com.restservice.service.TournamentService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import static java.time.Month.MARCH;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TournamentControllerTest {

    @Mock
    private TournamentService tournamentService;

    private TournamentController underTest;
    
    private Match match;

    private Set<Match> customerMatches;
    private static final long VALID_ID = 10L;
    private static final String VALID_LICENCE = "ABC123";
    private final Set<String> VALID_LICENCES = Set.of("ABC123", "XYZ456");

    @Before
    public void setUp() {
        underTest = new TournamentController(tournamentService);
        match = Match.builder().playerA("PlayerA").playerB("PlayerB").build();
        customerMatches = Set.of(match);
    }

    @Test
    public void testListForCustomer() {
        val invalidId = 1L;

        doReturn(customerMatches).when(tournamentService).listAllByCustomerId(VALID_ID);
        doReturn(emptySet()).when(tournamentService).listAllByCustomerId(invalidId);

        // Happy Path
        assertThat(underTest.listForCustomer(VALID_ID, null).getBody()).isNotEmpty();

        // Customer id doesn't exist
        assertThat(underTest.listForCustomer(invalidId, null).getBody()).isEmpty();

        // No customer id supplied
        assertThat(underTest.listForCustomer(null, null).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testListByLicence() {
        val invalidLicence = "ABC12";

        doReturn(customerMatches).when(tournamentService).listAllByLicence(VALID_LICENCE);
        doReturn(emptySet()).when(tournamentService).listAllByLicence(invalidLicence);

        // Happy Path
        assertThat(underTest.listByLicence(VALID_LICENCE, null).getBody()).isNotEmpty();

        // Licence code doesn't exist
        assertThat(underTest.listByLicence(invalidLicence, null).getBody()).isEmpty();

        // No licence code supplied
        assertThat(underTest.listByLicence(null, null).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testListAllByLicences() {
        val invalidLicences = Set.of("ABC", "XYZ");

        doReturn(customerMatches).when(tournamentService).listAllForLicences(VALID_LICENCES);
        doReturn(emptySet()).when(tournamentService).listAllForLicences(invalidLicences);

        // Happy Path
        assertThat(underTest.listAllByLicences(VALID_LICENCES, null).getBody()).isNotEmpty();

        // Licence code doesn't exist
        assertThat(underTest.listAllByLicences(invalidLicences, null).getBody()).isEmpty();

        // No licence code supplied
        assertThat(underTest.listAllByLicences(null, null).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(underTest.listAllByLicences(emptySet(), null).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testSummaryType() {
        // Summary Type 'AvB' supplied
        var now = LocalDate.of(2021, MARCH, 30).atStartOfDay(ZoneId.systemDefault()).toInstant();
        match.setSummary("PlayerA vs PlayerB");
        match.setStartDate(Date.from(now));

        doReturn(customerMatches).when(tournamentService).listAllByCustomerId(VALID_ID);
        var matches = underTest.listForCustomer(VALID_ID, "AvB").getBody();
        assertThat(matches).isNotNull();
        assertThat(matches.iterator().next().getSummary()).isEqualTo("PlayerA vs PlayerB");

        doReturn(customerMatches).when(tournamentService).listAllByLicence(VALID_LICENCE);
        matches = underTest.listByLicence(VALID_LICENCE, "AvB").getBody();
        assertThat(matches).isNotNull();
        assertThat(matches.iterator().next().getSummary()).isEqualTo("PlayerA vs PlayerB");

        doReturn(customerMatches).when(tournamentService).listAllForLicences(VALID_LICENCES);
        matches = underTest.listAllByLicences(VALID_LICENCES, "AvB").getBody();
        assertThat(matches).isNotNull();
        assertThat(matches.iterator().next().getSummary()).isEqualTo("PlayerA vs PlayerB");

        // Summary type 'AvBTime' supplied
        now = LocalDate.of(2021, MARCH, 13).atStartOfDay(ZoneId.systemDefault()).toInstant();
        match.setSummary("PlayerA vs PlayerB, starts in X minutes.");
        match.setStartDate(Date.from(now));

        doReturn(customerMatches).when(tournamentService).listAllByCustomerId(VALID_ID);
        matches = underTest.listForCustomer(VALID_ID, "AvBTime").getBody();
        assertThat(matches).isNotNull();
        assertThat(matches.iterator().next().getSummary()).contains("started");

        doReturn(customerMatches).when(tournamentService).listAllByLicence(VALID_LICENCE);
        matches = underTest.listByLicence(VALID_LICENCE, "AvBTime").getBody();
        assertThat(matches).isNotNull();
        assertThat(matches.iterator().next().getSummary()).contains("started");

        doReturn(customerMatches).when(tournamentService).listAllForLicences(VALID_LICENCES);
        matches = underTest.listAllByLicences(VALID_LICENCES, "AvBTime").getBody();
        assertThat(matches).isNotNull();
        assertThat(matches.iterator().next().getSummary()).contains("started");
    }
}