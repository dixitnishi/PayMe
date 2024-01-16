package com.walletapplication.payme.model.outbound;


import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EmailDetails {

    private String receiverEmail;
    private String subject;
    private String body;

}
