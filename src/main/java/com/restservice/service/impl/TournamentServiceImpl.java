package com.restservice.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.restservice.model.Customer;
import com.restservice.model.Match;
import com.restservice.model.Tournament;
import com.restservice.service.TournamentService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;
import static org.springframework.util.StringUtils.hasText;

@Service
public class TournamentServiceImpl implements TournamentService {
    
    private final Gson GSON = new GsonBuilder().create();

    @Override
    public Set<Match> listAllByCustomerId(long customerId) {
        val data = loadData();
        return data.stream()
                .filter(customer -> customer.getCustomerId() == customerId)
                .findFirst()
                .map(customer -> customer.getTournaments().stream()
                        .map(Tournament::getMatches)
                        .flatMap(Collection::stream)
                        .collect(toSet()))
                .orElse(emptySet());
    }

    @Override
    public Set<Match> listAllByLicence(String licence) {
        if (!hasText(licence)) return emptySet();
        
        val data = loadData();
        return data.stream()
                .map(Customer::getTournaments)
                .flatMap(Collection::stream)
                .filter(tournament -> tournament.getLicence().equals(licence))
                .map(Tournament::getMatches)
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    @Override
    public Set<Match> listAllForLicences(Set<String> licences) {
        if (isNull(licences) || licences.isEmpty()) return emptySet();
        
        val data = loadData();
        return data.stream()
                .map(Customer::getTournaments)
                .flatMap(Collection::stream)
                .filter(tournament -> licences.contains(tournament.getLicence()))
                .map(Tournament::getMatches)
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    private Set<Customer> loadData() {
        val data = new HashSet<Customer>();
        try {
            val reader = new FileReader("src/main/resources/data.json");
            val customers = GSON.fromJson(reader, Customer[].class);
            reader.close();
            data.addAll(Arrays.asList(customers));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
