package com.xyz.threatre.aggregator.offer;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Ticket;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public class AfternoonStratgey implements OfferStrategy{

    @Override
    public BigDecimal applyOffer(List<Ticket> tickets, Show show, String city, String theatre) {
        LocalTime showTime = show.getTime().toLocalTime();
        if(showTime.isAfter(LocalTime.NOON) && showTime.isBefore(LocalTime.of(18,0))){
           BigDecimal total = tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
           total.multiply(BigDecimal.valueOf(0.2));
        }
        return BigDecimal.ZERO;
    }
}
