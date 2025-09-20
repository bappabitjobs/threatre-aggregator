package com.xyz.threatre.aggregator.integration;

import com.xyz.threatre.aggregator.dto.MovieDTO;

import java.util.List;

public interface TheatreMovieProvider {
     List<MovieDTO> fetchMoviesByCity(String city);

     String getTheatreName();
}
