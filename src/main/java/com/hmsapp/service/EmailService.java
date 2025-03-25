package com.hmsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired private JavaMailSender javaMailSender;

    public void sendBookingConfirmation(String email, String guestName, String bookingDetails){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Booking Confirmation");
        message.setText("Dear " + guestName + ",\n\nThank you for your booking!\n\nDetails:\n" + bookingDetails +
        "\n\nBest Regards,\nYour EDITH.");


        message.setFrom("smnhussainy786@gmail.com");
        javaMailSender.send(message);
    }

}
