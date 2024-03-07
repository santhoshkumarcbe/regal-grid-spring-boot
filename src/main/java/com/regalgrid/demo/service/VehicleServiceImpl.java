package com.regalgrid.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.dto.Vehicle;
import com.regalgrid.demo.model.User;
import com.regalgrid.demo.model.UserVehicle;
import com.regalgrid.demo.model.VehicleModel;
import com.regalgrid.demo.repository.UserRepository;
import com.regalgrid.demo.repository.UserVehicleRepository;
import com.regalgrid.demo.repository.VehicleModelRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleModelRepository vehicleModelRepository;

    @Autowired
    UserVehicleRepository userVehicleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean postVehicle(VehicleModel vehicle) {
        VehicleModel exsistVehicleModel = vehicleModelRepository.findByVehicleModel(vehicle.getVehicleModel());
        if (exsistVehicleModel != null) {
            return false;
        }
        vehicleModelRepository.save(vehicle);
        return true;
    }

    @Override
    public List<VehicleModel> getAllVehicles() {
        return vehicleModelRepository.findAll();
    }

    @Override
    public VehicleModel getByModel(String vehicleModel) {
        return vehicleModelRepository.findByVehicleModel(vehicleModel);
    }

    @Override
    public int getChargingTime(String vehicleModel, int currentCharge, int expectedCharge) {

        if (currentCharge > expectedCharge + 10 || expectedCharge > 100) {
            throw new Error("expected charge should be minimum 10 % greater than current charge");
        }

        VehicleModel vehicle = vehicleModelRepository.findByVehicleModel(vehicleModel);
        int chargingTime = vehicle.getChargingTime(); // charing time in seconds
        int neededCharge = expectedCharge - currentCharge;
        int chargeDuration = (chargingTime / 100) * neededCharge;

        return chargeDuration; // charge duration in seconds
    }

    @Override
    public int getexpectedcharge(String vehicleModel, int currentCharge, int time) {
        VehicleModel vehicle = vehicleModelRepository.findByVehicleModel(vehicleModel);
        int chargingTime = vehicle.getChargingTime();
        int neededCharge = (time * 100) / chargingTime;
        int expectedCharge = neededCharge + currentCharge;
        return expectedCharge;
        
    }

    @Override
    public String postUserVehicle(UserVehicle userVehicle, String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            return "userName not found";
        }
        userVehicle.setUserName(userName);
        boolean isVehicleExist = vehicleModelRepository.existsByVehicleModel(userVehicle.getVehiclemodel());
        boolean isUserVehicleExist = userVehicleRepository.existsByVehicleNameAndUserName(userVehicle.getVehicleName(), userVehicle.getUserName());
        if (!isVehicleExist) {
            return "Vehicle model not found";
        }
        if (isUserVehicleExist) {
            return "vehicleName already found";
        }

        userVehicleRepository.save(userVehicle);
        return "true";

    }

    @Override
    public Vehicle getVehicle(String username) {
        List<UserVehicle> userVehicles = userVehicleRepository.findAllByUserName(username);
        List<VehicleModel> vehicleModels = vehicleModelRepository.findAll();
        Vehicle vehicle = new Vehicle(vehicleModels, userVehicles);
        return vehicle;
    }

   
}
