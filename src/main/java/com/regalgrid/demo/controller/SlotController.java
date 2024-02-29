package com.regalgrid.demo.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.regalgrid.demo.model.Slot;
import com.regalgrid.demo.service.SlotService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@RestController
@RequestMapping("slot")
public class SlotController {
    @Autowired
    SlotService slotService;

    @PostMapping("/post")
    public ResponseEntity<String> createSlot(@RequestBody Slot slot) {
        try {
            String response = slotService.bookSlot(slot);
            if (response.equals("created")) {
                return new ResponseEntity<>("slot booked", HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/bookedslots")
    public ResponseEntity<List<Slot>> getAllBookedSlots(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date,
    @RequestParam String chargingStationId) {
        try {
            List<Slot> slots = slotService.getAllBookedSlots(date, chargingStationId);
            return new ResponseEntity<>(slots, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/availableslots")
    public ResponseEntity<List<Slot>> getAllAvailableSlots(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date,
    @RequestParam String chargingStationId) {
        try {
            List<Slot> slots = slotService.getAllAvailableSlots(date, chargingStationId);
            return new ResponseEntity<>(slots, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getallslotsbydate")
    public ResponseEntity<List<Slot>> getAllSlotsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date,
    @RequestParam String chargingStationId) {
        try {
            List<Slot> slots = slotService.getAllAvailableSlotsByDate(date, chargingStationId);
            return new ResponseEntity<>(slots, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
