package com.ciscotraining.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Disabled
@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @MockitoBean
    private JavaMailSender javaMailSender;

    @Test
    void testSendMail() {
        emailService.sendEmail("gamma699669@gmail.com",
                "Testing Java Mail Service",
                "Hi, Aap kaise hai?");
    }

}
