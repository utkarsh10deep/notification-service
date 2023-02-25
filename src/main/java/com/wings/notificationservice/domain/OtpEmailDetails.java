package com.wings.notificationservice.domain;

import lombok.Data;

@Data
public class OtpEmailDetails extends EmailDetails {
    private String purpose;
    private String otp;
}
