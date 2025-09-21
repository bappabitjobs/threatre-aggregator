package com.xyz.threatre.aggregator.integration;

import com.xyz.threatre.aggregator.dto.MovieDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrpcThreatreAdapter implements TheatreMovieProvider{
    private final String theatreName;
    private final String apiUrl;

    public GrpcThreatreAdapter(String theatreName, String apiUrl) {
        this.theatreName = theatreName;
        this.apiUrl = apiUrl;
    }

    //ADD logic 
    @Override
    public List<MovieDTO> fetchMoviesByCity(String city) {
        return List.of();
    }

    @Override
    public String getTheatreName() {
        return "";
    }
}
