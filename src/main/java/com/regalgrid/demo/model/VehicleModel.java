package com.regalgrid.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document( collection = "Vehicle")
public class VehicleModel {
    @Id
    private String id;
    private String vehicleType; // two wheeler || four wheeler
    private String vehicleModel; // e.g. tata nexon, hyundai iqnix
    private String batteryCapacity;  // in kwh
    private int chargingTime;   // estimated charging time from 0 - 100% in seconds
    private float chargeDrainPerKm; // percentage of charge reduced during per kilometer
}
