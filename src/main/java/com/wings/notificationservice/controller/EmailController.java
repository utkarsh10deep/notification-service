package com.wings.notificationservice.controller;

import com.wings.notificationservice.domain.AlertEmailDetails;
import com.wings.notificationservice.domain.EmailDetails;
import com.wings.notificationservice.domain.OtpEmailDetails;
import com.wings.notificationservice.service.EmailService;
import com.wings.notificationservice.service.WingsExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.wings.notificationservice.constants.AppConstants.HEADER_KEY_ID;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Value("${service.authentication.key}")
    private String serviceAuthenticationKey;

    @Autowired
    private WingsExecutorService executor;

    @PostMapping("/sendSimpleEmail")
    public ResponseEntity<String> sendMessage(@RequestBody EmailDetails emailDetails, @RequestHeader(HEADER_KEY_ID) String authenticationKey) {
        if(!authenticationKey.equals(serviceAuthenticationKey)) {
           return ResponseEntity.badRequest().build();
        }
        Runnable sendSimpleEmailTask = () -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailDetails.getFromEmail());
            simpleMailMessage.setTo(emailDetails.getToEmail());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getBody());
            emailService.sendMessage(simpleMailMessage);
        };
        executor.execute(sendSimpleEmailTask);
        return ResponseEntity.ok("Email request has been posted successfully");
    }

    @PostMapping("/sendOwOtpEmail")
    public ResponseEntity<?> sendOwTemplateMessage(@RequestBody OtpEmailDetails emailDetails, @RequestHeader(HEADER_KEY_ID) String authenticationKey) {
        if(!authenticationKey.equals(serviceAuthenticationKey)) {
            return ResponseEntity.badRequest().build();
        }
        Runnable sendOwOtpEmailTask = () -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailDetails.getFromEmail());
            simpleMailMessage.setTo(emailDetails.getToEmail());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getBody());
            emailService.sendOwOtpMessage(simpleMailMessage, emailDetails.getOtp(), emailDetails.getPurpose());
        };
        executor.execute(sendOwOtpEmailTask);
        return ResponseEntity.ok("Email request has been posted successfully");
    }

    @PostMapping("/sendOwAlertEmail")
    public ResponseEntity<?> sendOwAlertEmail(@RequestBody AlertEmailDetails emailDetails, @RequestHeader(HEADER_KEY_ID) String authenticationKey) {
        if(!authenticationKey.equals(serviceAuthenticationKey)) {
            return ResponseEntity.badRequest().build();
        }
        Runnable sendOwAlertEmailTask = () -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailDetails.getFromEmail());
            simpleMailMessage.setTo(emailDetails.getToEmail());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getBody());
            emailService.sendOwAlertMessage(
                    simpleMailMessage,
                    emailDetails.getCandidateName(),
                    emailDetails.getAadhaar(),
                    emailDetails.getOfferValue());
        };
        executor.execute(sendOwAlertEmailTask);
        return ResponseEntity.ok("Email Request has been posted successfully");
    }
}
