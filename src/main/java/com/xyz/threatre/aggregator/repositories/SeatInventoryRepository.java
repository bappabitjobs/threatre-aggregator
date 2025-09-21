package com.xyz.threatre.aggregator.repositories;

import com.xyz.threatre.aggregator.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatInventoryRepository extends JpaRepository<Seat, Long> {
}
