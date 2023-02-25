package com.wings.notificationservice.domain;

import lombok.Data;

@Data
public class AlertEmailDetails extends EmailDetails{
    private String candidateName;
    private String aadhaar;
    private String offerValue;
}
