package com.regalgrid.demo.service;

import java.util.List;

import com.regalgrid.demo.dto.Vehicle;
import com.regalgrid.demo.model.UserVehicle;
import com.regalgrid.demo.model.VehicleModel;

public interface VehicleService {

    boolean postVehicle(VehicleModel vehicle);

    List<VehicleModel> getAllVehicles();

    VehicleModel getByModel(String vehicleModel);

    int getChargingTime(String vehicleModel, int currentCharge, int expectedCharge);

    String postUserVehicle(UserVehicle userVehicle, String userName);

    Vehicle getVehicle(String username);

    int getexpectedcharge(String vehicleModel, int currentCharge, int time);
    
}
