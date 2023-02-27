package com.wings.notificationservice.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.wings.notificationservice.domain.SmsBody;
import com.wings.notificationservice.service.WingsExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static com.wings.notificationservice.constants.AppConstants.HEADER_KEY_ID;

@RestController
public class SMSController {
    @Value("${service.authentication.key}")
    private String serviceAuthenticationKey;

    @Autowired
    private WingsExecutorService executor;

    @Value("${sms.account.sid}")
    private String SMS_ACCOUNT_SID;
    @Value("${sms.auth.token}")
    private String SMS_AUTH_TOKEN;
    @Value("${sms.number}")
    private String SMS_NUMBER;


    @PostMapping("/sendSMS")
    public ResponseEntity<?> sendOwMessageCode(@RequestBody SmsBody body, @RequestHeader(HEADER_KEY_ID) String authenticationKey) {
        if(!authenticationKey.equals(serviceAuthenticationKey)) {
            return ResponseEntity.badRequest().build();
        }
        Runnable sendSmsTask = () -> {
            Twilio.init(SMS_ACCOUNT_SID, SMS_AUTH_TOKEN);
            Message message = Message.creator(
                            new PhoneNumber(body.getNumber()),
                            new PhoneNumber(SMS_NUMBER),
                            body.getMessage())
                    .create();
        };
        executor.execute(sendSmsTask);
        return ResponseEntity.ok("Send SMS was submitted successfully");
    }
}
