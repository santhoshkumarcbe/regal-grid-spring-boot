package com.regalgrid.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.regalgrid.demo.dto.ChargingStationDistance;
import com.regalgrid.demo.model.ChargingStation;
import com.regalgrid.demo.service.ChargingStationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping("chargingstation")
public class ChargingStationController {
    @Autowired
    ChargingStationService chargingStationService;

    @PostMapping("post")
    public ResponseEntity<String> createChargingStation(@RequestBody ChargingStation chargingStation) {
        try {
            String response = chargingStationService.createChargingStation(chargingStation);
            if (response.equals("created")) {
                return new ResponseEntity<>("charging station created", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getall")
    public ResponseEntity<List<ChargingStationDistance>> getAllChargingStation(@RequestParam double userLatitude,
    @RequestParam double userLongitude) {
        try {
            List<ChargingStationDistance> chargingStations = chargingStationService.findAllChargingStationSortByDistance(userLatitude, userLongitude);
            return new ResponseEntity<>(chargingStations, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getallbydealerName/{dealerName}")
    public ResponseEntity<List<ChargingStation>> getAllChargingStationsByDealerName(@PathVariable String dealerName) {
        try {
            List<ChargingStation> chargingStations = chargingStationService.findAllChargingStationByDealerName(dealerName);
            return new ResponseEntity<>(chargingStations, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


  
}
