package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.dto.SeatInventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TheatreSeatInventoryService {
//Each theatre updates its own inventory
    @Autowired
    private KafkaTemplate<String, SeatInventoryDTO> kafkaTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    // Allocate or update seat inventory and propagate via Kafka
    public void allocateOrUpdateSeatsKafka(SeatInventoryDTO seatInventory) {
       // Save/update in theatre's own DB (not shown)
        kafkaTemplate.send("theatre-seat-inventory", seatInventory.getShowId().toString(),seatInventory);
    }

    // Allocate or update seat inventory and propagate via REST API
    public void allocateOrUpdateSeatsRestApi(SeatInventoryDTO seatInventory) {
        // Save/update in theatre's own DB (not shown)
        String xyzShowApiUrl = "https://xyz.com/api/theatre/seat-inventory";
        restTemplate.postForEntity (xyzShowApiUrl, seatInventory, Void.class);
    }
}
