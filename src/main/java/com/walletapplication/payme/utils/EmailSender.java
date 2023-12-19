package com.walletapplication.payme.utils;

import com.walletapplication.payme.model.outbound.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendEmail(EmailDetails emailDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDetails.getReceiverEmail());
        message.setSubject(emailDetails.getSubject());
        message.setText(emailDetails.getBody());

        message.setFrom(senderEmail);

        javaMailSender.send(message);
    }
}
