package com.xyz.threatre.aggregator.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Data
public class TicketBookingRequestDTO {
    private String theaterId;
    private LocalDate date;
    private LocalTime showTime;
    private List<String> seatNumbers;
    private String customerName;
    private String customerEmail;

}
