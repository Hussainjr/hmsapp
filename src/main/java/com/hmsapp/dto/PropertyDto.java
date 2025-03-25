package com.hmsapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDto {

    private Long id;

    private Integer noOfBathroom;

    private Integer noOfBedroom;

    private Integer noOfGuest;

    private String name;

    private String city;

    private String country;

}
