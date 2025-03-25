package com.hmsapp.dto;

import com.hmsapp.entity.Property;
import lombok.Data;

@Data
public class BookingDto {

    private Long id;

    private String guestName;

    private Integer noOfGuest;

    private String mobile;

    private String email;

    private Property property;

}
