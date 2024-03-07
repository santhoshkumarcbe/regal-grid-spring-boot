package com.regalgrid.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.regalgrid.demo.dto.Vehicle;
import com.regalgrid.demo.model.UserVehicle;
import com.regalgrid.demo.model.VehicleModel;
import com.regalgrid.demo.service.VehicleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin
@RequestMapping("vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @PostMapping("postvehiclebrand")
    public ResponseEntity<String> postVehicle(@RequestBody VehicleModel vehicle) {
        try {
            boolean response = vehicleService.postVehicle(vehicle);

            if (response) {
                return new ResponseEntity<>("vehicle added successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("vehicle brand already exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping("postuservehicle/{userName}")
    public ResponseEntity<String> postVehicle(@RequestBody UserVehicle userVehicle,
            @PathVariable("userName") String userName) {
        try {
            String response = vehicleService.postUserVehicle(userVehicle, userName);
            System.out.println("post user vehicle response : " + response);
            if (response.equals("true")) {
                return new ResponseEntity<>("user vehicle added successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("getall")
    public ResponseEntity<List<VehicleModel>> getAllVehicles() {
        try {
            List<VehicleModel> vehicles = vehicleService.getAllVehicles();
            return new ResponseEntity(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getbymodel/{vehicleModel}")
    public ResponseEntity<VehicleModel> getByModel(@PathVariable("vehicleModel") String vehicleModel) {
        try {
            VehicleModel vehicle = vehicleService.getByModel(vehicleModel);
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getestimatedchargingtime/{vehicleModel}/{currentBatteryPercentage}/{expectedBatteryPercentage}")
    public ResponseEntity<Integer> getChargingTime(@PathVariable("vehicleModel") String vehicleModel,
            @PathVariable("currentBatteryPercentage") int currentCharge,
            @PathVariable("expectedBatteryPercentage") int expectedCharge) {
        try {
            int fullChargeTime = vehicleService.getChargingTime(vehicleModel, currentCharge, expectedCharge);
            return new ResponseEntity<>(fullChargeTime, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getexpectedcharge/{vehicleModel}/{currentBatteryPercentage}/{time}")
    public ResponseEntity<Integer> getexpectedcharge(@PathVariable("vehicleModel") String vehicleModel,
            @PathVariable("currentBatteryPercentage") int currentCharge,
            @PathVariable("time") int time) {
        try {
            int fullChargeTime = vehicleService.getexpectedcharge(vehicleModel, currentCharge, time);
            return new ResponseEntity<>(fullChargeTime, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("getvehicle")
    public ResponseEntity<Vehicle> getVehicle(@RequestParam String username) {
        try {
            Vehicle vehicle = vehicleService.getVehicle(username);
            if (vehicle != null) {
                return new ResponseEntity<>(vehicle, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}