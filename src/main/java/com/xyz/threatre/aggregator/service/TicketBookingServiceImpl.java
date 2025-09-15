package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.categories.TicketStatus;
import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.TheaterSeat;
import com.xyz.threatre.aggregator.entities.Ticket;
import com.xyz.threatre.aggregator.exceptions.NoSeatAvailable;
import com.xyz.threatre.aggregator.repositories.SeatRepository;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import com.xyz.threatre.aggregator.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketBookingServiceImpl implements TicketBookingService{
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public TicketBookingServiceImpl(ShowRepository showRepository, SeatRepository seatRepository, TicketRepository ticketRepository) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket bookTicket(Long theatreId, LocalDateTime showTime, List<Long> preferredSeatsIds, String customerName, String customerEmail){
      Show show = showRepository.findByRoom_Threatre_IdAndShowTime(theatreId,showTime)
              .orElseThrow(() -> new RuntimeException("Show not found for theatre and time"));
     List<TheaterSeat> seats = seatRepository.findAllById(preferredSeatsIds);
      Ticket ticket = new Ticket();
      ticket.setShow(show);
      ticket.setCustomerName(customerName);
      ticket.setSeat(seats.get(0));
      ticket.setStatus(TicketStatus.BOOKED);
      ticketRepository.save(ticket);
      return ticket;
    }
}
