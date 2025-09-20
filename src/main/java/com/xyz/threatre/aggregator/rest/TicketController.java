package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.dto.BookingResponseDTO;
import com.xyz.threatre.aggregator.dto.TicketBookingRequestDTO;
import com.xyz.threatre.aggregator.entities.Ticket;
import com.xyz.threatre.aggregator.response.BookingResult;
import com.xyz.threatre.aggregator.rest.request.TicketBookingRequest;
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

    @Autowired
    private TicketBookingService ticketBookingService;

    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> ticketBooking(@RequestBody TicketBookingRequestDTO ticketBookingRequest) {
        BookingResponseDTO bookingResponseDTO = ticketBookingService.bookTickets(ticketBookingRequest);
           return ResponseEntity.ok(bookingResponseDTO);
    }

    @PostMapping("/book-multiple")
    public ResponseEntity<List<Ticket>> bookMultipleTickets(
            @RequestParam Long showId,
            @RequestParam List<Long> seatIds,
            @RequestParam String customerName,
            @RequestParam String customerEmail)  {
        try{
            List<Ticket> tickets = ticketBookingService.bookTickets(showId, seatIds, customerName, customerEmail);
            return ResponseEntity.ok(tickets);
        }catch(InterruptedException | ExecutionException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }
    }

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
