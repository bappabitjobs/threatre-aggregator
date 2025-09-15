package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.entities.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketBookingService {
     Ticket bookTicket(Long theatreId, LocalDateTime showTime, List<Long> preferredSeatsIds, String customerName, String customerEmail);
}
