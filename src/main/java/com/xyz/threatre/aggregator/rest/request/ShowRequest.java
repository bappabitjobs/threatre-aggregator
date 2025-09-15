package com.xyz.threatre.aggregator.rest.request;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ShowRequest {
    private LocalDateTime showStartTime;
    private LocalDate showDate;
    private Integer theaterId;
    private Integer movieId;
}
