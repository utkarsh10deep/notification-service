package com.wings.notificationservice.domain;

import lombok.Data;


@Data
public class Secrets {
    private String awsAccessKey;
    private String awsSecretKey;
    private String region;
    private String serviceAuthenticationKey;
}


