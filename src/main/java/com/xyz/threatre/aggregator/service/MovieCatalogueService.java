package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.entities.Movie;
import com.xyz.threatre.aggregator.exceptions.MovieNotFound;
import com.xyz.threatre.aggregator.repositories.MovieCatalogueRepository;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public interface MovieCatalogueService {
  List<Movie> getMoviesByCity(String city);
}
