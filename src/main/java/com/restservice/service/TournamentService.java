package com.restservice.service;

import com.restservice.model.Match;
import com.restservice.model.Tournament;

import java.util.Set;

public interface TournamentService {
    /**
     * Get all {@link Tournament}s for the given customer id.
     * @param customerId The customer id;
     * @return Set of {@link Match}es.
     */
    Set<Match> listAllByCustomerId(long customerId);

    /**
     * Get all {@link Tournament}s for the given licence code.
     * @param licence The licence code.
     * @return Set of {@link Match}es.
     */
    Set<Match> listAllByLicence(String licence);

    /**
     * Get all {@link Tournament}s for the given licence codes.
     * @param licences The licence codes.
     * @return Set of {@link Match}es.
     */
    Set<Match> listAllForLicences(Set<String> licences);

}
