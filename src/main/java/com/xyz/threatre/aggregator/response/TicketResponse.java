package com.xyz.threatre.aggregator.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private LocalDateTime time;
    private LocalDate date;
    private String movieName;
    private String theaterName;
    private String address;
    private String bookedSeats;
    private Integer totalPrice;
}
