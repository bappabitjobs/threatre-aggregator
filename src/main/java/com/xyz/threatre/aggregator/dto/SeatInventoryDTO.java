package com.xyz.threatre.aggregator.dto;

import lombok.Data;

import java.util.Map;

@Data
public class SeatInventoryDTO {
    private Long showId;
    private String theatreId;
    private Map<String,String> seatStaus;
}
