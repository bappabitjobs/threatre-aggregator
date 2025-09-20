package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.dto.MovieCatalogueResponse;
import com.xyz.threatre.aggregator.service.MovieAggregatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogue")
public class MovieCatalogueController {
    private final MovieAggregatorService movieAggregatorService;

    MovieCatalogueController(MovieAggregatorService movieAggregatorService){
        this.movieAggregatorService = movieAggregatorService;
    }
    // ex : movies/by-city?city=Bangalore
    @GetMapping("/by-city")
    public MovieCatalogueResponse getMoviesByCity(@RequestParam String city){
        return movieAggregatorService.getMoviesByCity(city);
    }

}
