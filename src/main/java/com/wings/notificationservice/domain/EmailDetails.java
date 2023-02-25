package com.wings.notificationservice.domain;

import lombok.Data;

@Data
public class EmailDetails {
    private String fromEmail;
    private String toEmail;
    private String subject;
    private String body;
}
