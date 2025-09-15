package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import com.xyz.threatre.aggregator.rest.request.ShowRequest;
import com.xyz.threatre.aggregator.service.ShowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/browse")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/show-by-movie")
    public List<Show> getShowByCityAndMovie(@ModelAttribute ShowRequest showRequest){
        return showService.getShowByMovieCityAndDate(showRequest.getCity(),showRequest.getMovieId(),showRequest.getShowDate());
    }
}
