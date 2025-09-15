package com.xyz.threatre.aggregator.repositories;

import com.xyz.threatre.aggregator.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie findByMovieName(String name);
}
