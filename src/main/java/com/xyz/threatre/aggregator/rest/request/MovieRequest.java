package com.xyz.threatre.aggregator.rest.request;

import com.xyz.threatre.aggregator.categories.Genre;
import com.xyz.threatre.aggregator.categories.Language;
import lombok.Data;

import java.util.Date;


@Data
public class MovieRequest {
    private String movieName;
    private Integer duration;
    private Double rating;
    private Date releaseDate;
    private Genre genre;
    private Language language;
}
