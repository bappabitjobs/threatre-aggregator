package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.categories.TicketStatus;
import com.xyz.threatre.aggregator.command.BookTicketCommand;
import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Seat;
import com.xyz.threatre.aggregator.entities.Ticket;
import com.xyz.threatre.aggregator.exceptions.ShowNotFound;
import com.xyz.threatre.aggregator.offer.OfferEngine;
import com.xyz.threatre.aggregator.repositories.SeatRepository;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import com.xyz.threatre.aggregator.repositories.TicketRepository;
import com.xyz.threatre.aggregator.response.BookingResult;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class TicketBookingServiceImpl implements TicketBookingService{
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final OfferEngine offerEngine;
    private final ExecutorService executors = Executors.newFixedThreadPool(10);

    public TicketBookingServiceImpl(ShowRepository showRepository, SeatRepository seatRepository, TicketRepository ticketRepository, OfferEngine offerEngine) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.offerEngine = offerEngine;
    }

    @Transactional
    public Ticket bookTicket(Long theatreId, LocalDateTime showTime, List<Long> preferredSeatsIds, String customerName, String customerEmail){
      Show show = showRepository.findByRoom_Threatre_IdAndShowTime(theatreId,showTime)
              .orElseThrow(() -> new ShowNotFound());
     List<Seat> seats = seatRepository.findAllById(preferredSeatsIds);
      Ticket ticket = new Ticket();
      ticket.setShow(show);
      ticket.setCustomerName(customerName);
      ticket.setSeat(seats.get(0));
      ticket.setStatus(TicketStatus.BOOKED);
      ticketRepository.save(ticket);
      return ticket;
    }

    public List<Ticket> bookTickets(Long showId, List<Long> seatIds, String customerName, String customerEmail) throws InterruptedException, ExecutionException {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ShowNotFound());
        List<Seat> seats = seatRepository.findAllById(seatIds);
        List<Future<Ticket>> futures = new ArrayList<>();

        List<Ticket> bookedTickets = Collections.synchronizedList(new ArrayList<>());
        for (Seat seat : seats) {
            BookTicketCommand command = new BookTicketCommand(
                    show, seat, customerName, customerEmail, ticketRepository,
                    ticket -> {
                        if (ticket != null) bookedTickets.add(ticket);
                    });
            futures.add(executors.submit(command, null));
        }
        // Wait for all bookings to finish
        for (Future<Ticket> future: futures) {
            future.get();
        }
        if (bookedTickets.size() != seatIds.size()) {
            throw new RuntimeException("Some seats could not be booked (already taken).");
        }
        return bookedTickets;
    }

    public BookingResult bookTicketsWithOffers (Long showId, List<Long> seatIds, String customerName, String customerEmail, String city, String theatre){
    Show show = showRepository.findById(showId)
            .orElseThrow(ShowNotFound::new);
        List<Seat> seats = seatRepository.findAllById(seatIds);
//        Check all seats exist
        if (seats.size() != seatIds.size()) {
            throw new RuntimeException("Some requested seats do not exist.");
        }
// Check all seats are available
        for (Seat seat: seats) {
            if (ticketRepository.findByShowAndSeat(show, seat).isPresent()) {
                throw new RuntimeException("One or more requested seats are already booked.");
            }
        }
            List<Ticket> tickets = seats.stream().map(seat -> {
                Ticket ticket = new Ticket();
                ticket.setShow(show);
                ticket.setSeat(seat);
                ticket.setBookingTime(java.time.LocalDateTime.now());
                ticket.setCustomerName(customerName);
                ticket.setCustomerEmail(customerEmail);
                ticket.setStatus(TicketStatus.BOOKED);
                // Set price if needed, e.g., ticket,setPrice(seat.getSeatType().getRice()
                return ticket;
            }).collect(java.util.stream.Collectors.toList());
            tickets = ticketRepository.saveAll(tickets);
            BigDecimal totalDiscount = offerEngine.applyOffers(tickets, show, city, theatre);
            BigDecimal totalPrice = tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal finalPrice = totalPrice.subtract(totalDiscount);
            return new BookingResult(tickets, totalPrice, totalDiscount, finalPrice);
        }
}
