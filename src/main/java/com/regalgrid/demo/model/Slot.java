package com.regalgrid.demo.model;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document( collection = "Slots")
public class Slot {
    @Id
    private String id;

    private String chargingStationId;

    private LocalDateTime startTime;

    private Duration duration;

    private LocalDateTime date;

    public Slot(LocalDateTime startTime, Duration duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

}
