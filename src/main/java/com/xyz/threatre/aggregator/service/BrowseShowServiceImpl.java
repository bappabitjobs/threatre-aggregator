package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.dto.TheatreShowInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BrowseShowServiceImpl implements BrowseShowService{
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<TheatreShowInfoDTO> browseTheatreRunningShow(String movieName, String town, LocalDate date){
        return Collections.emptyList();
    }
}
