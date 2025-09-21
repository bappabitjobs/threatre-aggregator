package com.xyz.threatre.aggregator.eventDriven;

import com.xyz.threatre.aggregator.dto.SeatInventoryDTO;
import com.xyz.threatre.aggregator.service.SeatInventorySyncService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SeatInventoryConsumer {
    private final SeatInventorySyncService seatInventorySyncService;

    public SeatInventoryConsumer(SeatInventorySyncService seatInventorySyncService) {
        this.seatInventorySyncService = seatInventorySyncService;
    }

    @KafkaListener(topics = "theatre-seat-inventory", groupId = "bookmyshow-seat")
    public void handleSeatInventoryUpdate (SeatInventoryDTO seatInventory) {
        seatInventorySyncService.syncSeatInventory(seatInventory);
    }
}
