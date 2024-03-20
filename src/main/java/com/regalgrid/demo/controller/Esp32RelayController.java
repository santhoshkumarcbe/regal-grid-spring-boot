package com.regalgrid.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.regalgrid.demo.model.Esp32;
import com.regalgrid.demo.service.Esp32Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("esp32")
public class Esp32RelayController {

    @Autowired
    Esp32Service esp32Service;

    @PostMapping("post")
    public ResponseEntity<String> postEsp32Station(@RequestBody Esp32 esp32) {
        try {
            boolean response = esp32Service.saveEsp32(esp32);
            if (response) {
                return new ResponseEntity<>("created",HttpStatus.OK);
            }
            return new ResponseEntity<>("check body",HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get/{stationName}")
    public ResponseEntity<Esp32> getEsp32(@PathVariable String stationName) {
        Esp32 esp32 = esp32Service.getEsp32ByStationName(stationName);
        return new ResponseEntity<>(esp32, HttpStatus.OK);
    }
    
    @PutMapping("onEsp32/{chargingStationId}/{time}")
    public ResponseEntity<String> updateEsp32(@PathVariable String chargingStationId, @PathVariable long milliseconds) {
        try {
            boolean response = esp32Service.turnOnEsp32(chargingStationId, milliseconds);
            if(response){
                return new ResponseEntity<>("turned on", HttpStatus.OK);
            }
            return new ResponseEntity<>("check request data", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("offEsp32")
    public ResponseEntity<String> turnOffEsp32(@RequestBody Esp32 esp32) {
        try {
            boolean response = esp32Service.offEsp32(esp32);
            if(response){
                return new ResponseEntity<>("turned off", HttpStatus.OK);
            }
            return new ResponseEntity<>("check request data", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
}
