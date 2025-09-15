package com.xyz.threatre.aggregator.rest.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketBookingRequest {
    private Long theatreId;
    private LocalDateTime showTime;
    private List<Long> preferredSeatIds;
    private String customerName;
    private String customerEmail;
}
