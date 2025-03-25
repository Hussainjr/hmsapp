package com.hmsapp.dto;

import com.hmsapp.entity.Property;
import com.hmsapp.entity.User;
import lombok.Data;

@Data
public class ReviewsDto {


    private Long id;

    private Integer rating;

    private String description;

    private Property property;

    private User user;
}
