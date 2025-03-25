package com.hmsapp.service;

import com.hmsapp.dto.RoomAvailabilityDto;

import java.util.List;

public interface RoomAvailabilityServiceInterface {

    public RoomAvailabilityDto saveRoom(RoomAvailabilityDto roomAvailabilityDto);

    public void deleteRoom(long id);

    public RoomAvailabilityDto updateRoom( long id, RoomAvailabilityDto roomAvailabilityDto);

    public List<RoomAvailabilityDto> getAllRoom();



}
