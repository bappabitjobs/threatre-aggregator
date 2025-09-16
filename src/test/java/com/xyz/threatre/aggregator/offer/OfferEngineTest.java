package com.xyz.threatre.aggregator.offer;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Ticket;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OfferEngineTest {

    @Test
    void testApplyOffers_withThirdTicketHalfPriceoffer() {
        OfferEngine engine = new OfferEngine();
        engine.addOffer(new ThirdTicketHalfPriceOffer(new HashSet<>(Arrays.asList("Mumbai", "Delhi")), new HashSet<>(Arrays.asList("PVR", "INOX"))));
        Show show = new Show();
        LocalDateTime time = LocalDateTime.of(2025, 9, 15, 14, 0);
        List<Ticket> tickets = Arrays.asList(
                createTicket(BigDecimal.valueOf(100)), createTicket(BigDecimal.valueOf(100)), createTicket(BigDecimal.valueOf(100))
        );
        BigDecimal discount = engine.applyOffers(tickets,show,"Bangalore","PVR");
    }

   private Ticket createTicket(BigDecimal price){
       Ticket ticket = new Ticket();
       ticket.setPrice(price);
       return ticket;
   }
}
