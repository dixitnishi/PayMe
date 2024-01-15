package com.walletapplication.payme.utils;

import com.walletapplication.payme.model.exceptions.GlobalWalletException;
import com.walletapplication.payme.model.outbound.EmailDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailQueueWorkerTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ListOperations<String, String> listOperations;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private EmailQueueWorker emailQueueWorker;

    @Test
    public void testProcessEmailQueue() {
        // Arrange
        String emailDetailsJson = "{\"receiverEmail\":\"test@example.com\",\"subject\":\"Test Subject\",\"body\":\"Test Body\"}";

        // Mock the behavior of redisTemplate
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.rightPop(anyString())).thenReturn(emailDetailsJson);

        // Act
        emailQueueWorker.processEmailQueue();

        // Assert
        verify(emailSender).sendEmail(any(EmailDetails.class));
    }

    @Test
    public void testProcessEmailQueueWithInvalidJson() {
        // Arrange
        String invalidJson = "Invalid JSON";

        // Mock the behavior of redisTemplate
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.rightPop(anyString())).thenReturn(invalidJson);

        // Act and Assert
        assertThrows(GlobalWalletException.class, () -> emailQueueWorker.processEmailQueue());
    }
}
