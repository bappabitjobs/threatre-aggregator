package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.entities.Movie;
import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.exceptions.MovieNotFound;
import com.xyz.threatre.aggregator.exceptions.ShowNotFound;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;

   public List<Show> getShowByMovieCityAndDate(String city, String movieId, LocalDate date){
      List<Show> shows =  showRepository.findShowByMovieCityAndDate(city, movieId, date);
      if(CollectionUtils.isEmpty(shows)){
          throw new ShowNotFound();
      }
      return shows;
   }

}
