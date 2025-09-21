package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.dto.MovieCatalogueResponse;
import com.xyz.threatre.aggregator.dto.MovieDTO;
import com.xyz.threatre.aggregator.exceptions.TheatreIntegrationException;
import com.xyz.threatre.aggregator.integration.TheatreMovieProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class MovieAggregatorService {

    private final List<TheatreMovieProvider> providers;

    public MovieCatalogueResponse getMoviesByCity (String city) {
        ExecutorService executor = Executors.newFixedThreadPool(providers.size());
        List<Future<List<MovieDTO>>> futures = new ArrayList<>();
        Map<String, String> errors = new ConcurrentHashMap<>();
        for (TheatreMovieProvider provider: providers) {
            futures.add(executor.submit(() -> {
                try {
                    return provider.fetchMoviesByCity(city);
                } catch (TheatreIntegrationException ex) {
                    log.error("Error fetching from {}: {}", ex.getTheatreName(), ex.getMessage());
                    errors.put(ex.getTheatreName(), ex.getMessage());
                    return Collections.emptyList();
                } catch (Exception ex) {
                    log.error("Unexpected error from {}: {}", provider.getTheatreName() , ex);
                    errors.put(provider.getTheatreName(), "Unexpected error: " + ex.getMessage());
                    return Collections.emptyList();
                }
            }));
        }
        Set<MovieDTO> allMovies = new HashSet<>();
        for (Future<List<MovieDTO>> future: futures) {
            try {
                allMovies.addAll(future.get());
            } catch (Exception ex) {
                log.error("Error in aggregator thread: ", ex);
            }
        }
        executor.shutdown();
        //Get Unique  Movie list
        List<MovieDTO> uniqueMovies = allMovies.stream()
                .collect(Collectors.toMap(MovieDTO::getName, m -> m, (m1, m2) -> m1))
                .values().stream().collect(Collectors.toList());
// TODO Add Errors too for transparency
        MovieCatalogueResponse response = new MovieCatalogueResponse (city, uniqueMovies, errors);
        return response;

    }
}
