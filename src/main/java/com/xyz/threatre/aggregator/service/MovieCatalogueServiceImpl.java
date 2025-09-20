package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.entities.Movie;
import com.xyz.threatre.aggregator.exceptions.MovieNotFound;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieCatalogueServiceImpl implements MovieCatalogueService{
    private final ShowRepository showRepository;

    public List<Movie> getMoviesByCity(String city){
        //throw exception when city
        List<Movie> movies =  showRepository.findByCityIgnoreCase(city);
        if(CollectionUtils.isEmpty(movies)){
            throw new MovieNotFound();
        }
        return movies;
    }
}
