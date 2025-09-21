package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.dto.BookingResponseDTO;
import com.xyz.threatre.aggregator.dto.TicketBookingRequestDTO;
import com.xyz.threatre.aggregator.entities.Ticket;
import com.xyz.threatre.aggregator.response.BookingResult;
import com.xyz.threatre.aggregator.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketBookingService ticketBookingService;

    public TicketController(TicketBookingService ticketBookingService) {
        this.ticketBookingService = ticketBookingService;
    }
 //Q3 : Book movie tickets by selecting a theatre, timing, and preferred seats for the day
    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> ticketBooking(@RequestBody TicketBookingRequestDTO ticketBookingRequest) {
        BookingResponseDTO bookingResponseDTO = ticketBookingService.bookTickets(ticketBookingRequest);
           return ResponseEntity.ok(bookingResponseDTO);
    }


    // Q6 -- Booking platform can introductory rollout offers in selected cities and theaters
    //To explain this offer , used localDB
    @PostMapping("/book-with-offers")
    public ResponseEntity<BookingResult> bookWithOffers(
            @RequestParam Long showId,
            @RequestParam List<Long> seatIds,
            @RequestParam String customerName,
            @RequestParam String customerEmail,
            @RequestParam String city,
            @RequestParam String theatre) throws InterruptedException {
        BookingResult result = ticketBookingService.bookTicketsWithOffers(showId, seatIds, customerName, customerEmail, city, theatre);
        return ResponseEntity.ok(result);
    }


}
