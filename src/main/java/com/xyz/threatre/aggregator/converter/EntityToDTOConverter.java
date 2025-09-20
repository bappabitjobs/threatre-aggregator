package com.xyz.threatre.aggregator.converter;

import com.xyz.threatre.aggregator.dto.ShowDTO;
import com.xyz.threatre.aggregator.entities.Show;

public class EntityToDTOConverter {

    public static Show showRequestToShow(ShowDTO show){
        //TODO write the code
        return new Show();
    }

    public static ShowDTO showDAOToDTO(Show show){
        //TODO write the code
        return ShowDTO.builder().build();
    }
}
