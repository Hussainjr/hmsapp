package com.hmsapp.dto;

import com.hmsapp.entity.Property;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomAvailabilityDto {

    private Long id;

    private String roomType;

    private Long totalRooms;

    private Long nightlyPrice;

    private Property property;

    private LocalDate date;

}
