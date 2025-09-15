package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.response.TicketResponse;
import com.xyz.threatre.aggregator.rest.request.TicketRequest;

public interface TicketService {
    TicketResponse ticketBooking(TicketRequest ticketRequest);
}
