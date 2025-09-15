package com.xyz.threatre.aggregator.repositories;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.TheaterSeat;
import com.xyz.threatre.aggregator.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<TheaterSeat> findByShowAndSeat(Show show, TheaterSeat seat);
}
