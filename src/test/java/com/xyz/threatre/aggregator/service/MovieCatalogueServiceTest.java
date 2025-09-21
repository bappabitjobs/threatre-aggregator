package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.categories.Genre;
import com.xyz.threatre.aggregator.entities.Movie;
import com.xyz.threatre.aggregator.exceptions.MovieNotFound;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieCatalogueServiceTest {

    @Test
    void testGetMoviesByCity_returnCorrectMovies(){
        ShowRepository repo = mock(ShowRepository.class);
        MovieCatalogueService service = new MovieCatalogueServiceImpl(repo);
       List<Movie> engMovies = Arrays.asList(
               Movie.builder().movieName("Avengers").genre(Genre.ANIMATION).build(),
               Movie.builder().movieName("InterStellar").genre(Genre.FICTION).build()
       );
     when(repo.findByCityIgnoreCase("Bangaluru")).thenReturn(engMovies);
     List<Movie> result = service.getMoviesByCity("Bangaluru");
     assertEquals(2,result.size());
     assertEquals("Avengers",result.get(0).getMovieName());
     assertEquals("InterStellar",result.get(1).getMovieName());
    }

    @Test
    void testGetMoviesByCity_throwsExceptionIfNoMoviesFound(){
        ShowRepository repo = mock(ShowRepository.class);
        MovieCatalogueService service = new MovieCatalogueServiceImpl(repo);
        when(repo.findByCityIgnoreCase("UnknowCity")).thenReturn(Collections.emptyList());
        MovieNotFound exception = assertThrows(MovieNotFound.class,()->service.getMoviesByCity("Unknown"));
        assertTrue(exception.getMessage().contains("No movie found"));
    }

}
