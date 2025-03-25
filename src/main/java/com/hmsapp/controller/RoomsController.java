package com.hmsapp.controller;

import com.hmsapp.dto.RoomAvailabilityDto;
import com.hmsapp.entity.Booking;
import com.hmsapp.entity.RoomAvailability;
import com.hmsapp.repository.BookingRepository;
import com.hmsapp.repository.RoomAvailabilityRepository;
import com.hmsapp.service.RoomAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomsController {

    @Autowired private RoomAvailabilityService roomAvailabilityService;
    @Autowired private RoomAvailabilityRepository roomAvailabilityRepository;
    @Autowired private BookingRepository bookingRepository;

    @PostMapping("/createRoom")
    public ResponseEntity<RoomAvailabilityDto> createRoom(@RequestBody RoomAvailabilityDto roomAvailabilityDto){
        RoomAvailabilityDto roomAvailabilityDto1 = roomAvailabilityService.saveRoom(roomAvailabilityDto);
        return new ResponseEntity<>(roomAvailabilityDto1, HttpStatus.OK);
    }

    @GetMapping("/search/rooms")
    public ResponseEntity<?> searchRooms(@RequestParam LocalDate fromDate,
                                                @RequestParam LocalDate toDate,
                                                @RequestParam String roomType,
                                                @RequestParam long propertyId)
        {
            List<RoomAvailability> rooms = roomAvailabilityRepository.findAvailableRooms(fromDate, toDate, roomType);
            for(RoomAvailability r:rooms){
                if(r.getTotalRooms()==0){
                    return new ResponseEntity<>("no rooms available", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRoom(@RequestParam long id){
        roomAvailabilityService.deleteRoom(id);
        return new ResponseEntity<>("room deleted ",HttpStatus.OK);
    }


}
