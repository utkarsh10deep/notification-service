package com.wings.notificationservice.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.wings.notificationservice.constants.AppConstants.*;

@Service
public class EmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;

    public void sendMessage(SimpleMailMessage simpleMailMessage) {
        this.mailSender.send(simpleMailMessage);
    }

    public void sendOwOtpMessage(SimpleMailMessage simpleMailMessage, String otp, String purpose) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = createMessageHelper(message);
            Template t = processTemplate(OTP_MESSAGE_TYPE);
            Map<String, Object> model = new HashMap<>();
            model.put(OTP_MESSAGE_TYPE, otp);
            model.put(PURPOSE, purpose);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            // Add attachment
//            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
            helper.setTo(Objects.requireNonNull(simpleMailMessage.getTo()));
            helper.setText(html, true);
            helper.setSubject(Objects.requireNonNull(simpleMailMessage.getSubject()));
            helper.setFrom(Objects.requireNonNull(simpleMailMessage.getFrom()));
            this.javaMailSender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void sendOwAlertMessage(SimpleMailMessage simpleMailMessage, String candidateName, String aadhaar, String offerValue) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            // Set mediaType
            MimeMessageHelper helper = createMessageHelper(message);
            Template t = processTemplate(ALERT_MESSAGE_TYPE);
            Map<String, Object> model = new HashMap<>();
            model.put(CANDIDATE_NAME, candidateName);
            model.put(AADHAAR, aadhaar);
            model.put(OFFER_VALUE, offerValue);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            // Add attachment
//            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
            helper.setTo(Objects.requireNonNull(simpleMailMessage.getTo()));
            helper.setText(html, true);
            helper.setSubject(Objects.requireNonNull(simpleMailMessage.getSubject()));
            helper.setFrom(Objects.requireNonNull(simpleMailMessage.getFrom()));
            javaMailSender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private Template processTemplate(String type) throws IOException {
        Template t = null;
        switch (type) {
            case OTP_MESSAGE_TYPE:
                t = config.getTemplate(OW_OTP_TEMPLATE_FILE);
                break;
            case ALERT_MESSAGE_TYPE:
                t = config.getTemplate(OW_ALERT_TEMPLATE_FILE);
                break;
            default:
                break;
        }
        return t;
    }

    private MimeMessageHelper createMessageHelper(MimeMessage message) throws MessagingException {
        return new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
    }
}
