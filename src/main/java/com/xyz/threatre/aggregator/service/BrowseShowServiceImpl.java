package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.dto.TheatreShowInfoDTO;

public class BrowseShowServiceImpl implements BrowseShowService{

    public TheatreShowInfoDTO browseThreatreRunningShow(){
        TheatreShowInfoDTO theatreShowInfoDTO = TheatreShowInfoDTO.builder().build();
        return theatreShowInfoDTO;
    }
}
