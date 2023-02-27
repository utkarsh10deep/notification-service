package com.wings.notificationservice.domain;

import lombok.Data;

@Data
public class SmsBody {
    private String message;
    private String number; //number with ISD prefix
}
