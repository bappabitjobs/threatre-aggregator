package com.xyz.threatre.aggregator.repositories;

import com.xyz.threatre.aggregator.entities.Movie;
import com.xyz.threatre.aggregator.entities.TheaterSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<TheaterSeat, Long> {
}
