package com.hmsapp.service;

import com.hmsapp.dto.BookingDto;

import java.util.List;

public interface BookingServiceInterface {

    public BookingDto saveBooking(BookingDto bookingDto);

    public void deleteBooking(long id);

    public BookingDto updateBooing(long id,BookingDto bookingDto);

    public List<BookingDto> getAllBooing();

}
