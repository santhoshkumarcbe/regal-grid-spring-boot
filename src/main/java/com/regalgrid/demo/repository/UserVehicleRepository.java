package com.regalgrid.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.regalgrid.demo.model.UserVehicle;

@Repository
public interface UserVehicleRepository extends MongoRepository<UserVehicle,String>{

    boolean existsByVehicleName(String vehicleName);
    
}
