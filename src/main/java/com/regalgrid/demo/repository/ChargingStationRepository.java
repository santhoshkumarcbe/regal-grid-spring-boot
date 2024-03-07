package com.regalgrid.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.regalgrid.demo.model.ChargingStation;

@Repository
public interface ChargingStationRepository extends MongoRepository<ChargingStation, String> {

    boolean existsByStationName(String string);

    List<ChargingStation> findAllByDealerName(String dealerName);
    
}
