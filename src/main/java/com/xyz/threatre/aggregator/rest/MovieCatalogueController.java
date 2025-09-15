package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.entities.Movie;
import com.xyz.threatre.aggregator.service.MovieCatalogueService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieCatalogueController {
    private final MovieCatalogueService movieCatalogueService;

    MovieCatalogueController(MovieCatalogueService movieCatalogueService){
        this.movieCatalogueService = movieCatalogueService;
    }

    public List<Movie> getMoviesByCity(@RequestParam String city){
        return movieCatalogueService.getMoviesByCity(city);
    }

}
