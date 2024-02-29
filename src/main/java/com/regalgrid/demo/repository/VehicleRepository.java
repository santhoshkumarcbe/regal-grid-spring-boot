package com.regalgrid.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.regalgrid.demo.model.Vehicle;

public interface VehicleRepository extends MongoRepository<Vehicle,String> {

    Vehicle findByVehicleModel(String vehicleModel);

    boolean existsByVehicleModel(String vehiclemodel);
    
}
