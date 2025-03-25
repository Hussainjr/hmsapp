package com.hmsapp.service;

import com.hmsapp.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

@Service
public class TwilioService{

    private final TwilioConfig twilioConfig;

    public TwilioService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;

        // Initialize Twilio with the configuration
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public void sendSms(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(toPhoneNumber),
                        new com.twilio.type.PhoneNumber(twilioConfig.getFromPhoneNumber()),
                        messageBody)
                .create();

        System.out.println("Message sent successfully: " + message.getSid());
    }
}
