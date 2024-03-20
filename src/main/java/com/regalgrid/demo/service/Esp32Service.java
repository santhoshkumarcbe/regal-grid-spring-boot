package com.regalgrid.demo.service;

import com.regalgrid.demo.model.Esp32;

public interface Esp32Service {

    boolean saveEsp32(Esp32 esp32);

    boolean turnOnEsp32(String chargingStationId, long milliseconds);

    Esp32 getEsp32ByStationName(String stationName);

    boolean offEsp32(Esp32 esp32);
    
}
