package com.xyz.threatre.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MovieDTO {
    private String name;
    private String genre;
    private String language;
}
