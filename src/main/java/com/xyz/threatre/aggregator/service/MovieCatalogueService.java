package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.entities.Movie;
import com.xyz.threatre.aggregator.repositories.MovieCatalogueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieCatalogueService {
  private final MovieCatalogueRepository movieCatalogueRepository;

  public List<Movie> getMoviesByCity(String city){
    return movieCatalogueRepository.findByCityIgnoreCase(city);
  }
}
