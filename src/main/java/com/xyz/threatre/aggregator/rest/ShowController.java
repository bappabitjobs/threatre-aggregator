package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.dto.request.ShowRequest;
import com.xyz.threatre.aggregator.service.BrowseShowService;
import com.xyz.threatre.aggregator.service.ShowService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/browse")
public class ShowController {
    private final ShowService showService;
    private final BrowseShowService browseShowService;

    public ShowController(ShowService showService, BrowseShowService browseShowService) {
        this.showService = showService;
        this.browseShowService = browseShowService;
    }

    // Q2. -- Browse theatres currently running the show (movie selected) in the town, including show timing by a chosen date
    @GetMapping("/show-theatreRunning")
    public void getTheatreRunning(@RequestParam String movieName,@RequestParam String town, @RequestParam String date ){
        //Date format yyyy-mm-dd
        browseShowService.browseTheatreRunningShow(movieName,town, LocalDate.parse(date));
    }
}
