package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.dto.BookingResponseDTO;
import com.xyz.threatre.aggregator.dto.TicketBookingRequestDTO;
import com.xyz.threatre.aggregator.entities.Ticket;
import com.xyz.threatre.aggregator.response.BookingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface TicketBookingService {
     BookingResponseDTO bookTickets(TicketBookingRequestDTO request);
     Ticket bookTicket(Long theatreId, LocalDateTime showTime, List<Long> preferredSeatsIds, String customerName, String customerEmail);
     List<Ticket> bookTickets (Long showId, List<Long> seatIds, String customerName, String customerEmail) throws InterruptedException, ExecutionException ;
     BookingResult bookTicketsWithOffers (Long showId, List<Long> seatIds, String customerName, String customerEmail, String city, String theatre);

     }
