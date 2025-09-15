package com.xyz.threatre.aggregator.repositories;

import com.xyz.threatre.aggregator.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieCatalogueRepository extends JpaRepository<Movie, Integer> {
    Movie findByMovieName(String name);

    List<Movie> findByCityIgnoreCase(String city);
}
