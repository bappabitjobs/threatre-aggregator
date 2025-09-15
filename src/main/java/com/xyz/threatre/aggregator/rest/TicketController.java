package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.entities.Ticket;
import com.xyz.threatre.aggregator.rest.request.TicketBookingRequest;
import com.xyz.threatre.aggregator.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketBookingService ticketBookingService;

    @PostMapping("/book")
    public ResponseEntity<Ticket> ticketBooking(@RequestBody TicketBookingRequest ticketBookingRequest) {

            Ticket ticket = ticketBookingService.bookTicket(ticketBookingRequest.getTheatreId(),
                    ticketBookingRequest.getShowTime(),ticketBookingRequest.getPreferredSeatIds(),ticketBookingRequest.getCustomerName(),ticketBookingRequest.getCustomerEmail());
           return ResponseEntity.ok(ticket);
        }    }
