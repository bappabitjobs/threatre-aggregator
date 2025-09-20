package com.xyz.threatre.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDTO {
    private String name;
    private String genre;
    private String language;
}
