package com.xyz.threatre.aggregator.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ShowTimingDTO {
    private Long showId;
    private LocalTime showTime;
    //Other fields like seat will add , if needed
}
