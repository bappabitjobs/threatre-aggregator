package com.xyz.threatre.aggregator.repositories;

import com.xyz.threatre.aggregator.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
