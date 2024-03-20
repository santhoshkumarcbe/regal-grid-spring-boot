package com.regalgrid.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.regalgrid.demo.model.Esp32;

public interface Esp32Repository extends MongoRepository<Esp32, String>{

    Esp32 findByStationName(String stationName);

    Esp32 findByChargingStationId(String chargingStationId);
    
}
