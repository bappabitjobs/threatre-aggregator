package com.xyz.threatre.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class MovieCatalogueResponse {
    private String city;
    private List<MovieDTO> movies;
    private Map<String,String> errors;
}
