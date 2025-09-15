package com.xyz.threatre.aggregator.repositories;

import com.xyz.threatre.aggregator.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    Theater findByAddress(String address);
}