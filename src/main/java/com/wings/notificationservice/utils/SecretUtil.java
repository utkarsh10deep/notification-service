package com.wings.notificationservice.utils;

import com.wings.notificationservice.domain.Secrets;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static com.wings.notificationservice.constants.AppConstants.*;

public class SecretUtil {
    private static String getHeaderKeyValue() {
        return new StringBuilder()
                .append(Decoder.decodeNumber(SECRET_SUB_KEY))
                .append(Decoder.decode(SECRET_INGREDIENT))
                .toString();
    }
    public static void initializeSecrets() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set(HEADER_KEY_ID, getHeaderKeyValue());
        headers.set(HEADER_SERVICE_ID, SERVICE_NAME);

        HttpEntity entityReq = new HttpEntity(headers);

        RestTemplate template = new RestTemplate();

        ResponseEntity<Secrets> respEntity = template
                .exchange(EndpointUtil.getSecretEndpoint(), HttpMethod.GET, entityReq, Secrets.class);
        Secrets secrets = respEntity.getBody();
        System.setProperty(AWS_ACCESS_KEY_ID, secrets.getAwsAccessKey());
        System.setProperty(AWS_SECRET_KEY_ID, secrets.getAwsSecretKey());
        System.setProperty(AWS_REGION_ID,secrets.getRegion());
        System.setProperty(SERVICE_KEY, secrets.getServiceAuthenticationKey());
        System.setProperty(SMS_ACCOUNT_SID, secrets.getTwilioAccountSid());
        System.setProperty(SMS_AUTH_TOKEN, secrets.getTwilioAuthToken());
        System.setProperty(SMS_NUMBER, secrets.getTwilioNumber());

    }
}
