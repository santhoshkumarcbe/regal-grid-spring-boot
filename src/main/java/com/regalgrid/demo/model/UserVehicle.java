package com.regalgrid.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document(collection = "UserVehicle")
public class UserVehicle {
    @Id
    private String id;
    private String userName;
    private String vehicleName;
    private String vehiclemodel;
    private int chargeAvailable; // current charge in vehicle
    private String location;
}
