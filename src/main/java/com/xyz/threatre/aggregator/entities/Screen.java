package com.xyz.threatre.aggregator.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SCREEN")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Screen {
    private Long id;
    private String Room;
    @ManyToOne
    private Theater theater;

}
