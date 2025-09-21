package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.dto.SeatInventoryDTO;
import com.xyz.threatre.aggregator.repositories.SeatInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class SeatInventorySyncService {

    @Autowired
    private SeatInventoryRepository seatInventoryRepository;
    // JPA
    @Autowired
    private CacheManager cacheManager;
    public void syncSeatInventory(SeatInventoryDTO seatInventory) {
        // 1. Update local DB
        //   seatInventoryRepository.saveOrUpdate(seatInventory);

        // 2. Update/invalidate cache for this show
        String cacheKey = seatInventory.getShowId().toString();
        if (cacheManager.getCache("seatInventoryCache") != null) {
            cacheManager.getCache("seatInventoryCache").evict(cacheKey);
        }

    }
}
