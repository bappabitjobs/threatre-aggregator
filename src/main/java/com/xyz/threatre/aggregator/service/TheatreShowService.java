package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.converter.EntityToDTOConverter;
import com.xyz.threatre.aggregator.dto.ShowDTO;
import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TheatreShowService {
    private final ShowRepository showRepository;

    @Autowired
    private KafkaTemplate<String, ShowDTO> kafkaTemplate;

    public TheatreShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public ShowDTO addShow(ShowDTO showDTO){
        //convert ShowDTO to Show Entity object

        //Show show = showRepository.save(show);
        //convert show to ShowDTO object ;
        //send message/event  to TheatreAggregator via Kafka
        kafkaTemplate.send("theatre-show-updates","ADD",showDTO);
        return showDTO;
    }

    public  void deleteShow(Long showId){

        showRepository.deleteById(showId);
        ShowDTO showDTO = ShowDTO.builder().id(showId).build();
        kafkaTemplate.send("theatre-show-updates","Delete",showDTO);

    }

    public ShowDTO updateShow(Long showId, ShowDTO  updateShow){
        Show show = showRepository.getReferenceById(showId);
      //  show.setShowTime(updateShow.getShowTime());
     //   show.setDate(updateShow.getDate());
     //   show.setScreen(updateShow.getScreen());
        Show updatedShow =  showRepository.save(show);
        return EntityToDTOConverter.showDAOToDTO(show);

    }

    public List<Show> getShowsForTheatreOnDay(Long theatreId, LocalDateTime dayStart, LocalDateTime dayEnd)
    {
        return showRepository.findByRoom_Theatre_IdAndShowTimeBetween (theatreId, dayStart, dayEnd);
    }

}
