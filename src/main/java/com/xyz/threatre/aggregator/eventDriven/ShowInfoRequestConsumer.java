package com.xyz.threatre.aggregator.eventDriven;

import com.xyz.threatre.aggregator.dto.TheatreShowInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ShowInfoRequestConsumer {
    @Autowired
    private CacheManager cacheManager;

    private final RestTemplate restTemplate = new RestTemplate();

    @KafkaListener(topics = "show-info-requests", groupId = "show-info-group")
    public void handleShowInfoRequest(String cacheKey, String apiUrl) {
        try {
            TheatreShowInfoDTO info = restTemplate.getForObject(apiUrl, TheatreShowInfoDTO.class);
            if (info != null && cacheManager.getCache("showCache") != null) {
                cacheManager.getCache("showCache").put(cacheKey, info);
            }

        } catch (Exception e) {
            // Log error, maybe retry or
        }

    }
}
