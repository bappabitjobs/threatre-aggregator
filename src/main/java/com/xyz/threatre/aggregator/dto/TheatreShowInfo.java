package com.xyz.threatre.aggregator.dto;

import lombok.Data;

import java.util.List;

@Data
public class TheatreShowInfo {
    String theatreName;
    String theatreAddress;
    String theatreId;
    private List<ShowTimingDTO> showTimingDTO;
}
