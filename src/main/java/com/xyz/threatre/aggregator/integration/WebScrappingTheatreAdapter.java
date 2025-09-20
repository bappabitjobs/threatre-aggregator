package com.xyz.threatre.aggregator.integration;

import com.xyz.threatre.aggregator.dto.MovieDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.util.List;

@Data
@Service
@Slf4j
public class WebScrappingTheatreAdapter implements TheatreMovieProvider{
    private final String theatreName;
    private final String webSiteUrl;

    @Override
    public List<MovieDTO> fetchMoviesByCity(String city) {
     //   Document doc = Jsoup.connect(webSiteUrl + "?city=" + city.get());
        return List.of();
    }

    @Override
    public String getTheatreName() {
        return theatreName;
    }
}
