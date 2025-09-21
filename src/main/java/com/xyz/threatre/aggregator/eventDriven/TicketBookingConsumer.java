package com.xyz.threatre.aggregator.eventDriven;

import org.hibernate.annotations.Comment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TicketBookingConsumer {
    @KafkaListener(topics = "ticket-booking-requests", groupId = "ticket-booking")
    public void handleBookingRequest(String message) {
    // Parse message, check DB/OTC bookings, atomically book if available
   // Respond via another topic or update DB/cache
   // This is where you ensure no double-booking with OTC
    }
}
