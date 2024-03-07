package com.regalgrid.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.regalgrid.demo.model.UserVehicle;

@Repository
public interface UserVehicleRepository extends MongoRepository<UserVehicle,String>{

    List<UserVehicle> findAllByUserName(String username);

    boolean existsByVehicleNameAndUserName(String vehicleName, String userName);
    
}
