package com.xyz.threatre.aggregator.entities;

import com.xyz.threatre.aggregator.categories.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TICKETS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Show show;

    @ManyToOne
    private Seat seat;

    private LocalDateTime bookingTime;

    private String customerEmail;
    private String customerName;

    private TicketStatus status;
    private BigDecimal price;

}
