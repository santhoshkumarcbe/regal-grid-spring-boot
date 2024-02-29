package com.regalgrid.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.regalgrid.demo.model.TransactionDetails;
import com.regalgrid.demo.service.RazorPayService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
@RequestMapping("payment")

public class PaymentController {
    @Autowired
    RazorPayService razorPayService;

    @GetMapping("/createTransaction/{amount}")
    public ResponseEntity<TransactionDetails> createTransaction(@PathVariable double amount) {
        try {
            TransactionDetails response = razorPayService.createTransaction(amount);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 
}