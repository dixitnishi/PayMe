package com.walletapplication.payme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.walletapplication.payme.model.exceptions.GlobalErrorCode;
import com.walletapplication.payme.model.exceptions.GlobalWalletException;
import com.walletapplication.payme.model.outbound.EmailDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @InjectMocks
    private EmailService emailService;

    @Mock
    private ListOperations<String, String> listOperations;
    @Test
    void sendEmail_shouldPushToRedisQueue() {
        // Arrange
        EmailDetails emailDetails = new EmailDetails("recipient@example.com", "Subject", "Body");
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        // Act
        emailService.sendEmail(emailDetails);

        // Assert
        verify(listOperations, times(1)).leftPush(eq("emailQueue"), anyString());
    }

    @Test
    void sendEmail_shouldThrowExceptionOnJsonProcessingError() throws Exception {
        // Arrange
        EmailDetails emailDetails = new EmailDetails("recipient@example.com", "Subject", "Body");
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        doThrow(new GlobalWalletException("Simulated error",LocalDateTime.now(), GlobalErrorCode.OBJECT_CONVERSION_ERROR)).when(listOperations).leftPush(anyString(), anyString());

        // Act & Assert
        GlobalWalletException exception = assertThrows(GlobalWalletException.class, () -> emailService.sendEmail(emailDetails));
        assertEquals(GlobalErrorCode.OBJECT_CONVERSION_ERROR, exception.getCode());
    }
}
