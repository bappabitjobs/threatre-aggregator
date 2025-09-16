package com.xyz.threatre.aggregator.offer;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Ticket;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        //Matches with theatre name
        BigDecimal discount = engine.applyOffers(tickets,show,"Bangalore","PVR");
        assertTrue(discount.compareTo(BigDecimal.valueOf(50)) ==0);
    }

    @Test
    void testApplyOffers_withAfternoonShowOffer() {
        OfferEngine engine = new OfferEngine();
        engine.addOffer(new AfternoonShowOffer());
        Show show = new Show();
        show.setShowTime(Time.valueOf("15:30:00"));
        List<Ticket> tickets = Arrays.asList(
                createTicket(BigDecimal.valueOf(100)), createTicket(BigDecimal.valueOf(100))
        );
        BigDecimal discount = engine.applyOffers(tickets, show, "Mumbai", "PVR");
        assertEquals(0,discount.compareTo(BigDecimal.valueOf(40)));
    }



    @Test
    void testApplyOffers_withBothoffers() {
        OfferEngine engine = new OfferEngine();
        engine.addOffer(new ThirdTicketHalfPriceOffer(new HashSet<>(Arrays.asList("Mumbai")), new HashSet<>(Arrays.asList("PVR"))
        ));
        engine.addOffer(new AfternoonShowOffer());
        Show show = new Show();
        show.setShowTime(Time.valueOf("15:30:00"));
        List<Ticket> tickets = Arrays.asList(
                createTicket(BigDecimal.valueOf(100)), createTicket(BigDecimal.valueOf(100)), createTicket(BigDecimal.valueOf(100)));
       BigDecimal discount = engine.applyOffers(tickets, show, "Mumbai", "PVR");
       //50% off on third ticket (50) + 20% of on total (60) = 110
        assertEquals(0,discount.compareTo(BigDecimal.valueOf(110)));
    }

    @Test
    void testApplyOffers_noEligibleOffers() {
        OfferEngine engine = new OfferEngine();
        engine.addOffer(new ThirdTicketHalfPriceOffer(new HashSet<>(Arrays.asList("Delhi")), new HashSet<>(Arrays.asList("INOX"))
        ));
        Show show = new Show();
        show.setShowTime(Time.valueOf("10:30:00"));
        List<Ticket> tickets = Arrays.asList(
                createTicket(BigDecimal.valueOf(100)), createTicket(BigDecimal.valueOf(100)), createTicket(BigDecimal.valueOf(100)));
        BigDecimal discount = engine.applyOffers(tickets, show, "Mumbai", "PVR");
        assertEquals(0,discount.compareTo(BigDecimal.ZERO));

    }


   private Ticket createTicket(BigDecimal price){
       Ticket ticket = new Ticket();
       ticket.setPrice(price);
       return ticket;
   }
}
