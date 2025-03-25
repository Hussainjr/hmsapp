package com.hmsapp.controller;

import com.hmsapp.dto.BookingDto;
import com.hmsapp.entity.RoomAvailability;
import com.hmsapp.repository.RoomAvailabilityRepository;
import com.hmsapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingsController {

    @Autowired private RoomAvailabilityRepository roomAvailabilityRepository;
    @Autowired private BookingService bookingService;

    @GetMapping("/search/rooms")
    public ResponseEntity<String> searchRoomsAndBook(@RequestParam LocalDate fromDate,
                                                @RequestParam LocalDate toDate,
                                                @RequestParam String roomType,
                                                @RequestParam long propertyId,
                                                @RequestBody BookingDto bookingDto) throws IOException {
            bookingService.searchingRoomsAndBooking(fromDate, toDate, roomType, propertyId, bookingDto);

            List<RoomAvailability> rooms = roomAvailabilityRepository.findAvailableRooms(fromDate, toDate, roomType);
            for(RoomAvailability r:rooms){
                if(r.getTotalRooms()==0){
                return new ResponseEntity<>("no rooms available", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            return new ResponseEntity<>(rooms+"  Booking created. Message and email send! ", HttpStatus.OK);
        }
        
}
