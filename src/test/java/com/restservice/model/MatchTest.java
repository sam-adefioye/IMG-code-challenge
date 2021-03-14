package com.restservice.model;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static java.time.Month.MARCH;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MatchTest {

    private Match underTest;

    @Before
    public void setUp() {
        underTest = Match.builder().build();
    }

    @Test
    public void setSummaryByType() {
        var now = LocalDate.of(2021, MARCH, 30).atStartOfDay(ZoneId.systemDefault()).toInstant();
        // Summary Type 'AvB' supplied
        underTest.setStartDate(Date.from(now));
        underTest.setPlayerA("A");
        underTest.setPlayerB("B");
        underTest.setSummaryByType("AvB");
        assertThat(underTest.getSummary()).isEqualTo("A vs B");

        // Summary Type 'AvBTime' supplied
        now = LocalDate.of(2021, MARCH, 13).atStartOfDay(ZoneId.systemDefault()).toInstant();
        underTest.setStartDate(Date.from(now));
        underTest.setSummaryByType("AvBTime");
        assertThat(underTest.getSummary()).contains("started");

    }

    @Test
    public void dto() {
        underTest.setMatchId(1L);
        underTest.setStartDate(new Date());
        val matchDto = underTest.dto();

        assertThat(matchDto.getMatchId()).isEqualTo(1L);
        assertThat(matchDto.getStartDate()).isNotNull();
    }
}