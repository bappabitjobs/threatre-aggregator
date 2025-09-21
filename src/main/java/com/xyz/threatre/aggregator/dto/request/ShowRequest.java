package com.xyz.threatre.aggregator.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ShowRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate showDate;
    private String city;
    private String movieId;
}
