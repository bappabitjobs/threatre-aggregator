package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TheatreService {
    private final ShowRepository showRepository;

    public TheatreService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public Show addShow(Show show){
        return showRepository.save(show);
    }

    public  void deleteShow(Long showId){
        showRepository.deleteById(showId);
    }

    public Show updateShow(Long showId, Show updateShow){
        Show show = showRepository.getReferenceById(showId);
        show.setTime(updateShow.getTime());
        show.setDate(updateShow.getDate());
        show.setRoom(updateShow.getRoom());
        return showRepository.save(show);
    }

    public List<Show> getShowsForTheatreOnDay(Long theatreId, LocalDateTime dayStart, LocalDateTime dayEnd)
    {
        return showRepository.findByRoom_Theatre_IdAndShowTimeBetween (theatreId, dayStart, dayEnd);
    }

}
