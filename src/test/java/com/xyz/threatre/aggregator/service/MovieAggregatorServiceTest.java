package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.dto.MovieCatalogueResponse;
import com.xyz.threatre.aggregator.dto.MovieDTO;
import com.xyz.threatre.aggregator.integration.TheatreMovieProvider;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieAggregatorServiceTest {

    @Test
    void testGetMoviesByCity_aggregatesAndDeduplicatesMovies() {
        // Mock providers

        TheatreMovieProvider provider1 = new TheatreMovieProvider() {

            @Override
            public List<MovieDTO> fetchMoviesByCity(String city) {
                MovieDTO m1 = MovieDTO.builder()
                        .name("Inception")
                        .genre("Sci-Fi")
                        .language("English")
                        .build();
                MovieDTO m2 = MovieDTO.builder()
                        .name("Dune")
                        .genre("Sci-Fi")
                        .language("English")
                        .build();
                return Arrays.asList(m1, m2);
            }
            @Override
            public String getTheatreName() { return "PVR"; }
        };

        TheatreMovieProvider provider2 = new TheatreMovieProvider() {
            @Override
            public List<MovieDTO> fetchMoviesByCity(String city) {
                MovieDTO m1 = MovieDTO.builder()
                        .name("Inception")
                        .genre("Sci-Fi")
                        .language("English")
                        .build();
                MovieDTO m3 = MovieDTO.builder()
                        .name("Barbie")
                        .genre("Comedy")
                        .language("English")
                        .build();
                return Arrays.asList(m1, m3);
            }
            @Override
            public String getTheatreName() { return "INOX"; }
        };
        MovieAggregatorService service = new MovieAggregatorService(Arrays.asList(provider1,provider2));
        MovieCatalogueResponse response = service.getMoviesByCity("Bangalore");
        assertEquals("Bangalore", response.getCity());
        assertNotNull(response.getMovies());
        // Should deduplicate "Inception"
        assertEquals(3, response.getMovies().size());
        assertTrue(response.getMovies().stream().anyMatch(m -> m.getName().equals("Inception")));
        assertTrue(response.getMovies().stream().anyMatch(m -> m.getName().equals("Dune")));
        assertTrue(response.getMovies().stream().anyMatch(m -> m.getName().equals("Barbie")));
    }

    @Test
    void testGetMoviesByCity_handlesProviderError() {
        TheatreMovieProvider provider1 = new TheatreMovieProvider() {
            @Override
            public List<MovieDTO> fetchMoviesByCity(String city) {
                throw new RuntimeException("API down");
            }

            @Override
            public String getTheatreName() {
                return "PVR";
            }
        };
        TheatreMovieProvider provider2 = new TheatreMovieProvider() {
            @Override
            public List<MovieDTO> fetchMoviesByCity(String city) {
                MovieDTO m1 = MovieDTO.builder()
                        .name("Oppenheimer")
                        .genre("Drama")
                        .language("English")
                        .build();
                return Collections.singletonList(m1);
            }

            @Override
            public String getTheatreName() {
                return "INOX";
            }
        };
        MovieAggregatorService service = new MovieAggregatorService(Arrays.asList(provider1, provider2));
        MovieCatalogueResponse response = service.getMoviesByCity("Delhi");
        assertEquals("Bangalore",response.getCity());
        assertNotNull(response.getCity());
    }
}
