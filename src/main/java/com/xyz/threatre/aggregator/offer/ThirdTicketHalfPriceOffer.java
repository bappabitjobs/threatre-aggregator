package com.xyz.threatre.aggregator.offer;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ThirdTicketHalfPriceOffer implements OfferStrategy {
    private final Set<String> eligibleCities;
    private final Set<String> eligibleTheatres;

    public ThirdTicketHalfPriceOffer(Set<String> eligibleCities, Set<String> eligibleTheatres) {
        this.eligibleCities = eligibleCities;
        this.eligibleTheatres = eligibleTheatres;
    }

    @Override
    public BigDecimal applyOffer(List<Ticket> tickets, Show show, String city, String theatre) {
        if (tickets.size() < 3) return BigDecimal.ZERO;
        if (eligibleCities.contains(city) || eligibleTheatres.contains(theatre))
            return BigDecimal.ZERO;
         BigDecimal ticketPrice = tickets.get(2).getPrice();
         return ticketPrice.multiply(BigDecimal.valueOf(0.5));
        }
    }
