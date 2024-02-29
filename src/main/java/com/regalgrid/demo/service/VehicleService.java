package com.regalgrid.demo.service;

import java.util.List;

import com.regalgrid.demo.model.UserVehicle;
import com.regalgrid.demo.model.Vehicle;

public interface VehicleService {

    boolean postVehicle(Vehicle vehicle);

    List<Vehicle> getAllVehicles();

    Vehicle getByModel(String vehicleModel);

    int getChargingTime(String vehicleModel, int currentCharge, int expectedCharge);

    String postUserVehicle(UserVehicle userVehicle, String userName);
    
}
