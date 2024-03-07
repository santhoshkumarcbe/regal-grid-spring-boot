package com.regalgrid.demo.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.regalgrid.demo.model.Slot;

public interface SlotRepository extends MongoRepository<Slot,String>{

    List<Slot> findAllByOrderByStartTimeAsc();

    List<Slot> findByStartTimeBetweenOrderByStartTimeAsc(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Slot> findByChargingStationIdAndStartTimeBetweenOrderByStartTimeAsc(String chargingStationId, LocalDateTime startOfDay,
            LocalDateTime endOfDay);

    List<Slot> findAllByChargingStationId(String chargingStationId);

    
}
