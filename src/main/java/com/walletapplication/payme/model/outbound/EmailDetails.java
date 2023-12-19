package com.walletapplication.payme.model.outbound;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class EmailDetails {

    private String receiverEmail;
    private String subject;
    private String body;

}
