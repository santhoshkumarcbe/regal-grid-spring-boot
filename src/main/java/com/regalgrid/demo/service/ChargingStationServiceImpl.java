package com.regalgrid.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.dto.ChargingStationDistance;
import com.regalgrid.demo.model.ChargingStation;
import com.regalgrid.demo.model.User;
import com.regalgrid.demo.repository.ChargingStationRepository;
import com.regalgrid.demo.repository.UserRepository;

@Service
public class ChargingStationServiceImpl implements ChargingStationService{

    private static final double EARTH_RADIUS_KM = 6371.0;

    private final MongoTemplate mongoTemplate;

    public ChargingStationServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChargingStationRepository chargingStationRepository;

    
    @Override
    public String createChargingStation(ChargingStation chargingStation) {
        User dealer = userRepository.getByUserName(chargingStation.getDealerName());
        if (dealer==null) {
            return "dealer not found";
        }
        if (!dealer.getUserRole().equals("dealer")) {
            return "charging station can be assigned only to dealers";
        }
        if (chargingStationRepository.existsByStationName(chargingStation.getStationName())) {
            return "station name already found";
        }
        chargingStationRepository.save(chargingStation);
        return "created";
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_KM * c; // Radius of Earth in km
        distance = Math.round(distance * 100.0) / 100.0;
        return distance;
    }

    List<ChargingStation> findAllChargingStations() {
        return mongoTemplate.findAll(ChargingStation.class);
    }

    @Override
    public List<ChargingStationDistance> findAllChargingStationSortByDistance(double userLatitude, double userLongitude) {
        List<ChargingStation> chargingStations = mongoTemplate.findAll(ChargingStation.class);
        List<ChargingStationDistance> stationDistances = new ArrayList<>();

        // Calculate distance for each charging station
        for (ChargingStation station : chargingStations) {
            String[] location = station.getLocation().split(",");
            double stationLatitude = Double.parseDouble(location[0]);
            double stationLongitude = Double.parseDouble(location[1]);

            double distance = calculateDistance(userLatitude, userLongitude, stationLatitude, stationLongitude);

            stationDistances.add(new ChargingStationDistance(station, distance));
        }

        // Sort stations by distance
        Collections.sort(stationDistances, Comparator.comparingDouble(ChargingStationDistance::getDistance));

        return stationDistances;
    }

    @Override
    public List<ChargingStation> findAllChargingStationByDealerName(String dealerName) {
        return chargingStationRepository.findAllByDealerName(dealerName);
    }
    
}
