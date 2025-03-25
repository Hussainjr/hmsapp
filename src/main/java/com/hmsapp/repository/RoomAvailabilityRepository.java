package com.hmsapp.repository;

import com.hmsapp.entity.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {

    @Query("select r from RoomAvailability r where r.roomType = :roomType and r.date between :fromDate and :toDate")
    List<RoomAvailability> findAvailableRooms(@Param("fromDate") LocalDate fromDate,
                                              @Param("toDate") LocalDate toDate,
                                              @Param("roomType") String roomType);



}