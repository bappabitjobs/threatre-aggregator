package com.xyz.threatre.aggregator.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TheatreShowInfoDTO {
    private String theatreName;
    private String theatreAddress;
    private String theatreId;
    private List<ShowTimingDTO> showTimingDTO;
}
