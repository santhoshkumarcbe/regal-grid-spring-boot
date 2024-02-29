package com.regalgrid.demo.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document( collection = "Authorities")

public class Authorities {

    @Id
    private String id;

    private ArrayList<String> admin;

    private ArrayList<String> dealer;
    
    private ArrayList<String> customer;
}
