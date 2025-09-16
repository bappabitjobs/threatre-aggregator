package com.xyz.threatre.aggregator.service;


import com.xyz.threatre.aggregator.entities.Show;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {
    List<Show> getShowByMovieCityAndDate(String city, String movieId, LocalDate date);


}
