package com.regalgrid.demo.dto;

import com.regalgrid.demo.model.ChargingStation;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChargingStationDistance {

    private ChargingStation chargingStation;

    private double distance;

    public ChargingStationDistance(ChargingStation chargingStation, double distance) {
        this.chargingStation = chargingStation;
        this.distance = distance;
    }

}
