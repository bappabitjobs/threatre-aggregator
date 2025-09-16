package com.xyz.threatre.aggregator.repositories;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Seat;
import com.xyz.threatre.aggregator.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Seat> findByShowAndSeat(Show show, Seat seat);
}
