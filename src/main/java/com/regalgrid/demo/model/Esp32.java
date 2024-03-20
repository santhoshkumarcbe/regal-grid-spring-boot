package com.regalgrid.demo.model;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Esp32 {
    @Id
    private String id;

    private String chargingStationId;

    private String stationName;
    
    private boolean onStatus;

    private long duration; // in milli second
}
