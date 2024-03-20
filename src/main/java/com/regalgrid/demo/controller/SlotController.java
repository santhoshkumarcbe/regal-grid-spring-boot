package com.regalgrid.demo.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/getallslotsbyusername/{username}")
    public ResponseEntity<List<Slot>> getAllByUsername(@PathVariable String username) {
        try {
            List<Slot> slots = slotService.getAllByUsername(username);
            return new ResponseEntity<>(slots, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findslotbyid/{id}")
    public ResponseEntity<Slot> findSlotById(@PathVariable String id) {
        try {
            Slot slot = slotService.findSlotById(id);
            if (slot != null) {
                return new ResponseEntity<>(slot, HttpStatus.OK);  
            }
            return new ResponseEntity<>(slot, HttpStatus.BAD_REQUEST);  

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("/getallbookedslots")
    public ResponseEntity<List<Slot>> getAllBookedSlotsByChargingStationId(
    @RequestParam String chargingStationId) {
        try {
            List<Slot> slots = slotService.getAllBookedSlotsByChargingStationId(chargingStationId);
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

    @PutMapping("/slotExpireByCurrentTime")
    public ResponseEntity<List<Slot>> slotExpireByCurrentTime(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date) {
        try {
            List<Slot> slots = slotService.slotExpireByCurrentTime(date);
            return new ResponseEntity<>(slots, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/setSlotExpired/{slotId}")
    public ResponseEntity<String> setSlotExpired(@PathVariable String slotId) {
        try {
            Slot slot = slotService.setSlotExpired(slotId);
            if (slot != null) {
                return new ResponseEntity<>("slot expired set to true", HttpStatus.OK);  
            }
            return new ResponseEntity<>("slot already expired or slot not found", HttpStatus.BAD_REQUEST);  

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
