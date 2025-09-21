package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.dto.ShowDTO;
import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.service.TheatreShowService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

//Q4 :  Theatres can create, update and delete shows for the day
@RestController
@RequestMapping("/api/theatre/shows")
public class TheatreShowController {
    private final TheatreShowService theatreShowService;

    public TheatreShowController(TheatreShowService theatreShowService) {
        this.theatreShowService = theatreShowService;
    }

    @PostMapping("/add")
    public ResponseEntity<ShowDTO> addShow(@RequestBody ShowDTO show) {
        return ResponseEntity.ok(theatreShowService.addShow(show));
    }

    // Update an existing show
    @PutMapping("/{showId}")
    public ResponseEntity<ShowDTO> updateShow(@PathVariable Long showId, @RequestBody ShowDTO showDTO) {
        return ResponseEntity.ok(theatreShowService.updateShow(showId, showDTO));
    }

   // Delete a show
    @DeleteMapping("/{showId}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long showId) {
        theatreShowService.deleteShow(showId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<Show>> getShowsForTheatreOnDay(
            @PathVariable Long theatreId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dayStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dayEnd) {
        return ResponseEntity.ok(theatreShowService.getShowsForTheatreOnDay(theatreId, dayStart, dayEnd));
    }
}
