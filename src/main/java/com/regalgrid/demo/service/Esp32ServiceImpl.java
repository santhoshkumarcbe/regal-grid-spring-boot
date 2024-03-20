package com.regalgrid.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.model.Esp32;
import com.regalgrid.demo.repository.Esp32Repository;

@Service
public class Esp32ServiceImpl implements Esp32Service{

    @Autowired
    Esp32Repository esp32Repository;

    @Override
    public boolean saveEsp32(Esp32 esp32) {
       Esp32 esp = esp32Repository.save(esp32);
       if (esp!=null) {
        return true;
       }
       return false;
    }

    @Override
    public boolean turnOnEsp32(String chargingStationId, long milliseconds) {
        Esp32 esp32 = esp32Repository.findByChargingStationId(chargingStationId);
        if (esp32 == null) {
            return false;
        }
        esp32.setDuration(milliseconds);
        esp32.setOnStatus(true);
        esp32.setId(esp32.getId());
        esp32Repository.save(esp32);
        return true;
    }

    @Override
    public Esp32 getEsp32ByStationName(String stationName) {
        return esp32Repository.findByStationName(stationName);
    }

    @Override
    public boolean offEsp32(Esp32 esp32) {
        Esp32 existEsp32 = esp32Repository.findByStationName(esp32.getStationName());
        if (existEsp32 == null) {
            return false;
        }
        existEsp32.setDuration(0);
        existEsp32.setOnStatus(false);
        existEsp32.setId(existEsp32.getId());
        esp32Repository.save(existEsp32);
        return true;
    }
}
