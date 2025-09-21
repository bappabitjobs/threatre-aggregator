package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.dto.TheatreShowInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class BrowseShowServiceImpl implements BrowseShowService {
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final Map<String, String> THEATRE_API_URLS = Map.of(
            "PVR", "https://pvr.com/api/shows?town=%s&date=%s",
            "INOX", "https://inox.com/api/shows?town=%s&date=%s"
            // mock data of theatres more as needed
    );

    @Override
    public List<TheatreShowInfoDTO> browseTheatreRunningShow(String movieName, String town, LocalDate date) {
        ExecutorService executor = Executors.newFixedThreadPool(THEATRE_API_URLS.size());
        List<Future<TheatreShowInfoDTO>> futures = new ArrayList<>();
        for (Map.Entry<String, String> entry : THEATRE_API_URLS.entrySet()) {
            String theatreName = entry.getKey();
            String apiUrl = String.format(entry.getValue(), town, date);

            futures.add(executor.submit(() -> {
                // 1. Try cache first
                String cacheKey = theatreName + ":" + movieName + ":" + date;
                TheatreShowInfoDTO cached = cacheManager.getCache("showCache") != null
                        ? cacheManager.getCache("showCache").get(cacheKey, TheatreShowInfoDTO.class)
                        : null;
                if (cached != null) {
                    return cached;
                }
                // 2. Try external API
                try {
                    TheatreShowInfoDTO info = restTemplate.getForObject(apiUrl + "&movie=" + movieName, TheatreShowInfoDTO.class);
                    if (info != null && cacheManager.getCache("showCache") != null) {
                        cacheManager.getCache("showCache").put(cacheKey, info);
                    }
                    return info;
                } catch (Exception e) {
                    // 3. If API fails, trigger event for async fetch (e.g., via Kafka)
                    kafkaTemplate.send("show-info-requests", cacheKey, apiUrl + "&movie=" + movieName);
                    return null; // Or return a placeholder
                }
            }));
        }
        List<TheatreShowInfoDTO> result = new ArrayList<>();
        for (Future<TheatreShowInfoDTO> future : futures) {
            try {
                TheatreShowInfoDTO info = future.get(2, TimeUnit.SECONDS); //timeout for responsiveness
                if (info != null) result.add(info);
            } catch (Exception ignored) {
                //log
            }
        }
        executor.shutdown();
        return result;
    }
}