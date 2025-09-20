package com.xyz.threatre.aggregator.integration;

import com.xyz.threatre.aggregator.dto.MovieDTO;
import com.xyz.threatre.aggregator.exceptions.TheatreIntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class RestApiTheatreAdapter implements TheatreMovieProvider{
    private final String theatreName;
    private final String apiUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public RestApiTheatreAdapter(String theatreName, String apiUrl) {
        this.theatreName = theatreName;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<MovieDTO> fetchMoviesByCity(String city) {
        try {
            String url = String.format(apiUrl,city);
            MovieDTO[] movies = restTemplate.getForObject(url, MovieDTO[].class);
            return movies != null ? Arrays.asList(movies): Collections.emptyList();
        } catch (Exception e) {
            throw new TheatreIntegrationException(theatreName, "Failed to fetch movies from "+ theatreName, e);
        }
    }

    @Override
    public String getTheatreName() {
        return theatreName;
    }
}
