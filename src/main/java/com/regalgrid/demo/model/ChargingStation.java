package com.regalgrid.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document( collection = "ChargingStation")

public class ChargingStation {
    @Id
    private String id;

    private String dealerName;

    private String stationName;

    private String stationtype; // two wheeler or four wheeler

    private String location;

    private double costPerMinute;
    
}