package com.hmsapp.service;

import com.hmsapp.Exception.RoomNotFoundException;
import com.hmsapp.dto.RoomAvailabilityDto;
import com.hmsapp.entity.Property;
import com.hmsapp.entity.RoomAvailability;
import com.hmsapp.repository.PropertyRepository;
import com.hmsapp.repository.RoomAvailabilityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RoomAvailabilityService implements RoomAvailabilityServiceInterface{

    @Autowired private ModelMapper modelMapper;
    @Autowired private RoomAvailabilityRepository roomAvailabilityRepository;
    @Autowired private PropertyRepository propertyRepository;

    @Override
    public RoomAvailabilityDto saveRoom(RoomAvailabilityDto roomAvailabilityDto) {
        Property property = propertyRepository.findById(roomAvailabilityDto.getProperty().getId())
                .orElseThrow(() -> new IllegalStateException("Property not found"));

        RoomAvailability mapped = modelMapper.map(roomAvailabilityDto, RoomAvailability.class);
        mapped.setProperty(property);
        RoomAvailability saved = roomAvailabilityRepository.save(mapped);
        RoomAvailabilityDto roomAvailabilityDto1 = modelMapper.map(saved, RoomAvailabilityDto.class);
        return roomAvailabilityDto1;
    }

    @Override
    public void deleteRoom(long id) {
        Optional<RoomAvailability> room = roomAvailabilityRepository.findById(id);
        if(room.isPresent()){
            roomAvailabilityRepository.deleteById(id);
        }else {
            throw new RoomNotFoundException("Room with ID " + id + " not found");
        }
    }

    @Override
    public RoomAvailabilityDto updateRoom(long id, RoomAvailabilityDto roomAvailabilityDto) {
        Optional<RoomAvailability> byId = roomAvailabilityRepository.findById(id);
        if(byId.isEmpty()){
            throw new RuntimeException("no room with an id "+id+" not found.");
        }
        RoomAvailability roomAvailability = modelMapper.map(roomAvailabilityDto, RoomAvailability.class);
        RoomAvailability saved = roomAvailabilityRepository.save(roomAvailability);
        RoomAvailabilityDto availabilityDto = modelMapper.map(saved, RoomAvailabilityDto.class);
        return availabilityDto;
    }

    @Override
    public List<RoomAvailabilityDto> getAllRoom() {
        List<RoomAvailability> all = roomAvailabilityRepository.findAll();
        Stream<RoomAvailabilityDto> roomAvailabilityDtoStream = all.stream().map((element) -> modelMapper.map(element, RoomAvailabilityDto.class));
        return (List<RoomAvailabilityDto>) roomAvailabilityDtoStream;
    }


}
