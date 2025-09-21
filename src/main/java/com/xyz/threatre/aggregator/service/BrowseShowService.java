package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.dto.TheatreShowInfoDTO;

import java.time.LocalDate;
import java.util.List;

public interface BrowseShowService {
    List<TheatreShowInfoDTO> browseTheatreRunningShow(String movieName, String town, LocalDate date);
}
