package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.dto.SeatInventoryDTO;
import com.xyz.threatre.aggregator.entities.Seat;
import com.xyz.threatre.aggregator.service.SeatInventorySyncService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatInventoryController {

    private final SeatInventorySyncService seatInventorySyncService;

    public SeatInventoryController(SeatInventorySyncService seatInventorySyncService) {
        this.seatInventorySyncService = seatInventorySyncService;
    }

    @PostMapping
    public void updateSeatInventory (@RequestBody SeatInventoryDTO seatInventory) {
        seatInventorySyncService.syncSeatInventory(seatInventory);
   }
}
