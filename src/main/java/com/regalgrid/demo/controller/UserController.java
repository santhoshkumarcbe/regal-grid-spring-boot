package com.regalgrid.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.regalgrid.demo.model.User;
import com.regalgrid.demo.service.UserService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;



@CrossOrigin
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping("addathorities/{userName}/{authority}")
    public ResponseEntity<String> addAuthority(@PathVariable("userName") String userName,
            @PathVariable("authority") String authority) {
        try {
            String response = userService.addAuthority(userName, authority);
            if (response.equals("updated")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("removeathorities/{userName}/{authority}")
    public ResponseEntity<String> removeAuthority(@PathVariable("userName") String userName,
            @PathVariable("authority") String authority) {
        try {
            String response = userService.removeAuthority(userName, authority);
            if (response.equals("updated")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updatewallet/{userName}/{amount}")
    public ResponseEntity<Double> updateWallet(@PathVariable("userName") String userName,
    @PathVariable("amount") double amount) {
        try {
            double response = userService.updateWallet(userName,amount);

                return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getwalletbalancebyusername/{username}")
    public ResponseEntity<Double> getWalletBalanceByUsername(@PathVariable String username) {
        try {
            double balance = userService.getWalletBalanceByUsername(username);
            return new ResponseEntity<>(balance,HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getall")
    public ResponseEntity<List<User>> getallUsers() {
        try {
            List<User> users = userService.getallUsers();
            return new ResponseEntity<>(users,HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getuserbyusername/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        try {
            User user = userService.getByUsername(username);
            return new ResponseEntity<>(user,HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
