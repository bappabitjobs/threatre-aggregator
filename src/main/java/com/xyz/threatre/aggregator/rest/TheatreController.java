package com.xyz.threatre.aggregator.rest;

import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.service.TheatreService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shows")
public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    public ResponseEntity<Show> addShow(@RequestBody Show show) {
        return ResponseEntity.ok(theatreService.addShow(show));
    }

// Update an existing show

    @PutMapping("/{showId}")
    public ResponseEntity<Show> updateShow(@PathVariable Long showId, @RequestBody Show show) {
        return ResponseEntity.ok(theatreService.updateShow (showId, show));

    }

// Delete a show

    @DeleteMapping("/{showId}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long showId) {
        theatreService.deleteShow(showId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<Show>> getShowsForTheatreOnDay(
            @PathVariable Long theatreId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dayStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dayEnd)
    {
    return ResponseEntity.ok(theatreService.getShowsForTheatreOnDay(theatreId, dayStart, dayEnd));
   }
}
