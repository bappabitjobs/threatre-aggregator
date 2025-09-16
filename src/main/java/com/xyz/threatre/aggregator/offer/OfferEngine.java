package com.xyz.threatre.aggregator.offer;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OfferEngine {
    private final List<OfferStrategy> offers = new ArrayList();

    public void addOffer(OfferStrategy offer) {
        offers.add(offer);
    }

    public BigDecimal applyOffers(List<Ticket> tickets, Show show, String city, String theatre){
        return offers.stream().
        map(offer -> offer.applyOffer(tickets, show, city, theatre)).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

}
