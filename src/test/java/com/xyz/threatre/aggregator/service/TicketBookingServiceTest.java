package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.entities.Seat;
import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Ticket;
import com.xyz.threatre.aggregator.offer.OfferEngine;
import com.xyz.threatre.aggregator.repositories.SeatRepository;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import com.xyz.threatre.aggregator.repositories.TicketRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketBookingServiceTest {

    @Test
    public void testBookticketsSuccess() throws InterruptedException, ExecutionException {
        ShowRepository showRepo = mock(ShowRepository.class);
        SeatRepository seatRepo = mock(SeatRepository.class);
        TicketRepository ticketRepo = mock(TicketRepository.class);
        OfferEngine offerEngine = mock(OfferEngine.class);
        Show show = new Show();
        show.setShowId(1L);

        Seat seat1 = new Seat();
        seat1.setId(101L);
        Seat seat2 = new Seat();
        seat1.setId(102L);
        when(showRepo.findById(1L)).thenReturn(Optional.of(show));
        when(seatRepo.findAllById(Arrays.asList(101L, 102L))).thenReturn(Arrays.asList(seat1, seat2));
        when(ticketRepo.findByShowAndSeat(show, seat1)).thenReturn(Optional.empty());
        when(ticketRepo.findByShowAndSeat(show, seat2)).thenReturn(Optional.empty());
        when(ticketRepo.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));
        TicketBookingService service = new TicketBookingServiceImpl(showRepo, seatRepo, ticketRepo, offerEngine);
        List<Ticket> tickets = service.bookTickets(1L, Arrays.asList(101L, 102L), "Bappa", "bappa@example.com");
        assertEquals(2, tickets.size());

    }

    @Test
    void testBookTickets_seatAlreadyBooked() throws InterruptedException {
        ShowRepository showRepo = mock(ShowRepository.class);
        SeatRepository seatRepo = mock(SeatRepository.class);
        TicketRepository ticketRepo = mock(TicketRepository.class);
        OfferEngine offerEngine = mock(OfferEngine.class);
        Show show = new Show();
        show.setShowId(1L);
        Seat seat1 = new Seat();
        seat1.setId(101L);
        Seat seat2 = new Seat();
        seat2.setId(102L);
        when(showRepo.findById(1L)).thenReturn(Optional.of(show));
        when(seatRepo.findAllById(Arrays.asList(101L, 102L))).thenReturn(Arrays.asList(seat1, seat2));
        when(ticketRepo.findByShowAndSeat(show, seat1)).thenReturn(Optional.of(new Seat()));
        when(ticketRepo.findByShowAndSeat(show, seat2)).thenReturn(Optional.empty());
        TicketBookingService service = new TicketBookingServiceImpl(showRepo, seatRepo, ticketRepo,offerEngine);
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                service.bookTickets(1L, Arrays.asList(101L, 102L), "Bappa", "bappa@example.com"));
        assertTrue(ex.getMessage().contains("Some seats could not be booked"));
    }
}
