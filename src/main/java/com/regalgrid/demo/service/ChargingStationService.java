package com.regalgrid.demo.service;

import java.util.List;

import com.regalgrid.demo.dto.ChargingStationDistance;
import com.regalgrid.demo.model.ChargingStation;

public interface ChargingStationService {

    String createChargingStation(ChargingStation chargingStation);

    List<ChargingStationDistance> findAllChargingStationSortByDistance(double userLatitude, double userLongitude);

    List<ChargingStation> findAllChargingStationByDealerName(String dealerName);
    
}
