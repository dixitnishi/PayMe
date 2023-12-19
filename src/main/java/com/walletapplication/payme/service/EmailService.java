package com.walletapplication.payme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapplication.payme.model.outbound.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void sendEmail(EmailDetails emailDetails) throws JsonProcessingException {
        String emailDetailsJson = convertEmailDetailsToJson(emailDetails);
        redisTemplate.opsForList().leftPush("emailQueue", emailDetailsJson);
    }

    private String convertEmailDetailsToJson(EmailDetails emailDetails) throws JsonProcessingException {
         ObjectMapper objectMapper = new ObjectMapper();
         return objectMapper.writeValueAsString(emailDetails);
    }
}
