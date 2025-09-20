package com.xyz.threatre.aggregator.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class ShowDTO {
    private Long id;
    private String theatreId;
    private String movieName;
    private LocalDate showDate;
    private LocalTime showTime;
}
