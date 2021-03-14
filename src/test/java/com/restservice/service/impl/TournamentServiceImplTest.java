package com.restservice.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TournamentServiceImplTest {

    private TournamentServiceImpl underTest;

    @Before
    public void setUp() {
        underTest = new TournamentServiceImpl();
    }

    @Test
    public void testListAllByCustomerId_HappyPath() {
        assertThat(underTest.listAllByCustomerId(10)).isNotEmpty();
    }

    @Test
    public void testListAllByCustomerId_NotExist() {
        assertThat(underTest.listAllByCustomerId(113)).isEmpty();
    }

    @Test
    public void testListAllByLicence_HappyPath() {
        assertThat(underTest.listAllByLicence("ABC123")).isNotEmpty();
    }

    @Test
    public void testListAllByLicence_NotExist() {
        assertThat(underTest.listAllByLicence("ABC")).isEmpty();
    }

    @Test
    public void testListAllByLicence_IsNull() {
        assertThat(underTest.listAllByLicence(null)).isEmpty();
    }

    @Test
    public void testListAllForLicences_HappyPath() {
        assertThat(underTest.listAllForLicences(Set.of("ABC123",  "XYZ456"))).isNotEmpty();
    }

    @Test
    public void testListAllForLicences_NotExist() {
        assertThat(underTest.listAllForLicences(Set.of("ABC12",  "XYZ45"))).isEmpty();
    }

    @Test
    public void testListAllForLicences_IsNull() {
        assertThat(underTest.listAllForLicences(null)).isEmpty();
    }
}