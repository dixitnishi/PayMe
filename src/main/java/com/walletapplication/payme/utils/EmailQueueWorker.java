package com.walletapplication.payme.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapplication.payme.model.exceptions.GlobalErrorCode;
import com.walletapplication.payme.model.exceptions.GlobalWalletException;
import com.walletapplication.payme.model.outbound.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailQueueWorker {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EmailSender emailSender;

    @Scheduled(fixedDelay = 1000) // Adjust the delay based on your needs
    public void processEmailQueue(){
        String emailDetailsJson = redisTemplate.opsForList().rightPop("emailQueue");

        if (emailDetailsJson != null) {
            EmailDetails emailDetails = convertJsonToEmailDetails(emailDetailsJson);
            emailSender.sendEmail(emailDetails);
        }
    }

    private EmailDetails convertJsonToEmailDetails(String json) {
        // Implement JSON to object conversion logic
        // You can use Jackson, Gson, or any other library for this
        // For simplicity, assume EmailDetails has fields like to, subject, body
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, EmailDetails.class);
        } catch (JsonProcessingException e) {
            throw new GlobalWalletException("Error occurred while converting object", LocalDateTime.now(), GlobalErrorCode.OBJECT_CONVERSION_ERROR);
        }
    }
}
