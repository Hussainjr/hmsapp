package com.hmsapp.service;

import com.hmsapp.dto.BookingDto;
import com.hmsapp.entity.Booking;
import com.hmsapp.entity.Property;
import com.hmsapp.repository.BookingRepository;
import com.hmsapp.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements BookingServiceInterface{

    @Autowired private ModelMapper modelMapper;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private TwilioService twilioService;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private PDFGenerator pdfGenerator;
    @Autowired private EmailService emailService;

    @Override
    public BookingDto saveBooking(BookingDto bookingDto) {
        return null;
    }

    @Override
    public void deleteBooking(long id) {

    }

    @Override
    public BookingDto updateBooing(long id, BookingDto bookingDto) {
        return null;
    }

    @Override
    public List<BookingDto> getAllBooing() {
        List<Booking> all = bookingRepository.findAll();
        List<BookingDto> bookingDtos = all.stream()
                .map((element) -> modelMapper.map(element, BookingDto.class)).toList();
        return bookingDtos;
    }

    public BookingDto searchingRoomsAndBooking(LocalDate fromDate, LocalDate toDate,
                                               String roomType, long propertyId, BookingDto bookingDto){
        Optional<Property> byId = propertyRepository.findById(propertyId);
        if(byId.isEmpty()){
            throw new IllegalStateException("propertyId "+propertyId+" not found");
        }
        Property property = byId.get();

        Booking bookings = modelMapper.map(bookingDto, Booking.class);
        bookings.setProperty(property);
        Booking savedBookings = bookingRepository.save(bookings);
        pdfGenerator.generatePdf("H:\\bookings_docs\\bookings_"+savedBookings.getId()+".pdf", savedBookings);
        twilioService.sendSms("+919948107089", "hey yo this is jessey pinkman");

        emailService.sendBookingConfirmation(bookingDto.getEmail(), bookingDto.getGuestName(),
                "Booking ID: "+ bookingDto.getId() + "\nDate: "+ fromDate);

        BookingDto bookingDto1 = modelMapper.map(savedBookings, BookingDto.class);
        return bookingDto1;
    }

}
