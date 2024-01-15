package com.walletapplication.payme.utils;

import com.walletapplication.payme.model.outbound.EmailDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailSenderTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailSender emailSender;

    @Test
    public void testSendEmail() {
        // Arrange
        EmailDetails emailDetails = new EmailDetails("test@example.com", "Subject", "Body");

        // Act
        emailSender.sendEmail(emailDetails);

        // Assert
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}
