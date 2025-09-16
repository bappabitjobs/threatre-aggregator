package com.xyz.threatre.aggregator.command;

import com.xyz.threatre.aggregator.categories.TicketStatus;
import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Seat;
import com.xyz.threatre.aggregator.entities.Ticket;
import com.xyz.threatre.aggregator.repositories.TicketRepository;
import com.xyz.threatre.aggregator.service.TicketBookingService;

import java.time.LocalDateTime;
import java.util.function.Consumer;

public class BookTicketCommand implements TicketCommand {

    private final Show show;
    private final Seat seat;
    private final String customerName;
    private final String customerEmail;
    private final TicketRepository ticketRepository;
    private final Consumer<Ticket> callback;

    public BookTicketCommand (Show show, Seat seat, String customerName, String customerEmail, TicketRepository ticketRepository, Consumer<Ticket> callBack){
        this.show = show;
        this.seat =seat;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.ticketRepository = ticketRepository;
        this.callback = callBack;
    }

    @Override
    public void run() {
        if (ticketRepository.findByShowAndSeat(show, seat).isPresent()) {
            callback.accept(null); // Indicate failure for this s
            return;
        }
        Ticket ticket = new Ticket();
        ticket.setShow(show);
        ticket.setSeat(seat);
        ticket.setBookingTime (LocalDateTime.now());
        ticket.setCustomerName (customerName);
        ticket.setCustomerEmail(customerEmail);
        ticket.setStatus(TicketStatus.BOOKED);
        Ticket savedTicket = ticketRepository.save(ticket);
        callback.accept(savedTicket);
    }
}


