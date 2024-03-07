package com.regalgrid.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.regalgrid.demo.model.VehicleModel;

public interface VehicleModelRepository extends MongoRepository<VehicleModel,String> {

    VehicleModel findByVehicleModel(String vehicleModel);

    boolean existsByVehicleModel(String vehiclemodel);
    
}
