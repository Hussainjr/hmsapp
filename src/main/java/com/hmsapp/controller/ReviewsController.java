package com.hmsapp.controller;

import com.hmsapp.dto.ReviewsDto;
import com.hmsapp.entity.Property;
import com.hmsapp.entity.Reviews;
import com.hmsapp.entity.User;
import com.hmsapp.repository.PropertyRepository;
import com.hmsapp.repository.ReviewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewsController {

    @Autowired private ReviewsRepository reviewsRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private PropertyRepository propertyRepository;

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody ReviewsDto reviewsDto,
                            @RequestParam long propertyId,
                            @AuthenticationPrincipal User user){

        Reviews reviews = modelMapper.map(reviewsDto, Reviews.class);
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new NoSuchElementException("property with id " + propertyId + "not found."));
//        Property property = propertyRepository.findById(propertyId).get();

        Reviews reviewsStatus = reviewsRepository.findByPropertyAndUser(property, user);
        if(reviewsStatus!=null) {
            return new ResponseEntity<>("review already given", HttpStatus.OK);
        }
        reviews.setProperty(property);
        reviews.setUser(user);
        Reviews saved = reviewsRepository.save(reviews);
        return new ResponseEntity<>("added", HttpStatus.OK);
    }

    @GetMapping("/user/reviews")
    public ResponseEntity<List<Reviews>> viewMyReviews(
            @AuthenticationPrincipal User user){
        List<Reviews> reviewsList = reviewsRepository.findByUser(user);
        return new ResponseEntity<>(reviewsList, HttpStatus.OK);

    }



}
