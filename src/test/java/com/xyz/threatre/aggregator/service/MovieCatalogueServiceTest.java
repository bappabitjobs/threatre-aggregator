package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.categories.Genre;
import com.xyz.threatre.aggregator.entities.Movie;
import com.xyz.threatre.aggregator.repositories.MovieCatalogueRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieCatalogueServiceTest {

    @Test
    void testGetMoviesByCity_returnCorrectMovies(){
        MovieCatalogueRepository repo = mock(MovieCatalogueRepository.class);
        MovieCatalogueService service = new MovieCatalogueService(repo);
       List<Movie> engMovies = Arrays.asList(
               Movie.builder().movieName("Avengers").genre(Genre.ANIMATION).build()
       );
when(repo.findByCityIgnoreCase("Bangaluru")).thenReturn(engMovies);
    }
}
