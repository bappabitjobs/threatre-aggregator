package com.xyz.threatre.aggregator.response;

import com.xyz.threatre.aggregator.entities.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingResult {
    private List<Ticket> tickets;
    private BigDecimal totalPrice;
    private BigDecimal disCountPrice;
    private BigDecimal finalPrice;
}
