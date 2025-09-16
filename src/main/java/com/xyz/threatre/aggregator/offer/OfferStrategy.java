package com.xyz.threatre.aggregator.offer;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Ticket;

import java.math.BigDecimal;
import java.util.List;

public interface OfferStrategy {
    BigDecimal applyOffer(List<Ticket> tickets, Show show, String city, String theatre);
}
