package com.regalgrid.demo.dto;

import java.util.List;

import com.regalgrid.demo.model.UserVehicle;
import com.regalgrid.demo.model.VehicleModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vehicle {
    private List<VehicleModel> vehicleModel;
    private List<UserVehicle> userVehicle;

    public Vehicle(List<VehicleModel> vehicleModel, List<UserVehicle> userVehicle){
        this.vehicleModel = vehicleModel;
        this.userVehicle = userVehicle;
    }
}
