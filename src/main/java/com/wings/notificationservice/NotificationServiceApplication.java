package com.wings.notificationservice;

import com.wings.notificationservice.configuration.EmailServiceConfig;
import com.wings.notificationservice.domain.Secrets;
import com.wings.notificationservice.utils.SecretUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SecretUtil.initializeSecrets();
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
