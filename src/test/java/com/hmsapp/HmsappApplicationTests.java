package com.hmsapp;

import com.hmsapp.dto.BookingDto;
import com.hmsapp.entity.Booking;
import com.hmsapp.entity.Property;
import com.hmsapp.repository.BookingRepository;
import com.hmsapp.repository.PropertyRepository;
import com.hmsapp.service.BookingService;
import com.hmsapp.service.EmailService;
import com.hmsapp.service.PDFGenerator;
import com.hmsapp.service.TwilioService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class HmsappApplicationTests {

//	@Autowired private BookingService bookingService;


	@Mock
	private PropertyRepository propertyRepository;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private PDFGenerator pdfGenerator;

	@Mock
	private TwilioService twilioService;

	@Mock
	private EmailService emailService;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private BookingService bookingService;

	private BookingDto bookingDto;
	private Property property;
	private Booking booking;

	@BeforeEach
	void setUp() {
		property = new Property();
		property.setId(1L);

		bookingDto = new BookingDto();
		bookingDto.setId(100L);
		bookingDto.setEmail("test@example.com");
		bookingDto.setGuestName("John Doe");

		booking = new Booking();
		booking.setId(100L);
		booking.setProperty(property);
	}

	@Test
	void testSearchingRoomsAndBooking_Success() {
		LocalDate fromDate = LocalDate.of(2025, 1, 1);
		LocalDate toDate = LocalDate.of(2025, 1, 10);
		long propertyId = 1L;
		String roomType = "Deluxe";

		when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
		when(modelMapper.map(bookingDto, Booking.class)).thenReturn(booking);
		when(bookingRepository.save(booking)).thenReturn(booking);
		when(modelMapper.map(booking, BookingDto.class)).thenReturn(bookingDto);

		BookingDto result = bookingService.searchingRoomsAndBooking(fromDate, toDate, roomType, propertyId, bookingDto);

		assertNotNull(result);
		assertEquals(bookingDto.getId(), result.getId());
		assertEquals(bookingDto.getEmail(), result.getEmail());

		verify(propertyRepository).findById(propertyId);
		verify(bookingRepository).save(booking);
		verify(pdfGenerator).generatePdf(anyString(), eq(booking));
		verify(twilioService).sendSms(anyString(), anyString());
		verify(emailService).sendBookingConfirmation(bookingDto.getEmail(), bookingDto.getGuestName(),
				"Booking ID: " + bookingDto.getId() + "\nDate: " + fromDate);
	}

	@Test
	void testSearchingRoomsAndBooking_PropertyNotFound() {
		LocalDate fromDate = LocalDate.of(2025, 1, 1);
		LocalDate toDate = LocalDate.of(2025, 1, 10);
		long propertyId = 99L;

		when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				bookingService.searchingRoomsAndBooking(fromDate, toDate, "Deluxe", propertyId, bookingDto)
		);

		assertEquals("propertyId 99 not found", exception.getMessage());
		verify(propertyRepository).findById(propertyId);
		verifyNoInteractions(bookingRepository, pdfGenerator, twilioService, emailService);
	}
}

