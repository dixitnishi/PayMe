package com.walletapplication.payme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapplication.payme.model.exceptions.GlobalErrorCode;
import com.walletapplication.payme.model.exceptions.GlobalWalletException;
import com.walletapplication.payme.model.outbound.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class EmailService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void sendEmail(EmailDetails emailDetails) {
        String emailDetailsJson = convertEmailDetailsToJson(emailDetails);
        redisTemplate.opsForList().leftPush("emailQueue", emailDetailsJson);
    }

    private String convertEmailDetailsToJson(EmailDetails emailDetails){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(emailDetails);
        } catch (JsonProcessingException e) {
            throw new GlobalWalletException("Error occurred while converting object", LocalDateTime.now(), GlobalErrorCode.OBJECT_CONVERSION_ERROR);
        }
    }
}
