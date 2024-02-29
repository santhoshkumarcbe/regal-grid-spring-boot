package com.regalgrid.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.model.User;
import com.regalgrid.demo.model.UserVehicle;
import com.regalgrid.demo.model.Vehicle;
import com.regalgrid.demo.repository.UserRepository;
import com.regalgrid.demo.repository.UserVehicleRepository;
import com.regalgrid.demo.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserVehicleRepository userVehicleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean postVehicle(Vehicle vehicle) {
       vehicleRepository.save(vehicle);
       return true;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle getByModel(String vehicleModel) {
        return vehicleRepository.findByVehicleModel(vehicleModel);
    }

    @Override
    public int getChargingTime(String vehicleModel, int currentCharge, int expectedCharge) {

        if (currentCharge > expectedCharge + 10 || expectedCharge > 100) {
            throw new Error("expected charge should be minimum 10 % greater than current charge");
        }
        
        Vehicle vehicle = vehicleRepository.findByVehicleModel(vehicleModel);
        int chargingTime = vehicle.getChargingTime(); // charing time in milliseconds
        int neededCharge = expectedCharge - currentCharge;
        int chargeDuration = (chargingTime/100) * neededCharge;

        return chargeDuration;
    }

    @Override
    public String postUserVehicle(UserVehicle userVehicle, String userName) {
        User user = userRepository.findByUserName(userName);
        userVehicle.setUserId(user.getUserId());
        boolean isVehicleExist = vehicleRepository.existsByVehicleModel(userVehicle.getVehiclemodel());
        boolean isUserVehicleExist = userVehicleRepository.existsByVehicleName(userVehicle.getVehicleName());
        if (!isVehicleExist) {
            return "Vehicle model not found";
        }
        if (isUserVehicleExist) {
            return "vehicleName already found";
        }

        userVehicleRepository.save(userVehicle);
        return "true";

    }}
