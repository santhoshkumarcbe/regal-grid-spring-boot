package com.regalgrid.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.DeleteExchange;

import com.regalgrid.demo.model.Authorities;
import com.regalgrid.demo.service.AuthoritiesService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@Controller
@RequestMapping("api/auth/authorities")

public class AuthoritiesController {

    @Autowired
    AuthoritiesService authoritiesService;

    @PostMapping("/post")
    public ResponseEntity<String> CreateAuthorities(@RequestBody Authorities authorities) {
        try {
            System.out.println(authorities);
            if (authoritiesService.createAuthority(authorities)) {
                return new ResponseEntity<String>("Authority created successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(
                        "Authorities can be created only once. Please update authorities if you want to edit",
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Authorities>> getAuthorities() {
        try {
            List<Authorities> authorities = authoritiesService.getAuthorities();
            return new ResponseEntity<>(authorities, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteExchange("/delete")
    public ResponseEntity<String> deleteAuthority() {
        try {
            if (authoritiesService.deleteAuthority()) {
                return new ResponseEntity<>("authorities deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/addauthority/{userrole}/{authority}")
    public ResponseEntity<String> addAuthority(@PathVariable("userrole") String userrole,
            @PathVariable("authority") String authority) {
        try {
            String response = authoritiesService.addAuthority(userrole, authority);
            if (response.equals("updated")) {
                return new ResponseEntity<>("authorities updated", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/removeauthority/{userrole}/{authority}")
    public ResponseEntity<String> removeAuthority(@PathVariable("userrole") String userrole,
            @PathVariable("authority") String authority) {
        try {
            String response = authoritiesService.removeAuthority(userrole, authority);
            if (response.equals("updated")) {
                return new ResponseEntity<>("authorities updated", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}