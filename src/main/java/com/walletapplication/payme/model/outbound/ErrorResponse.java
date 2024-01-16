package com.walletapplication.payme.model.outbound;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timeStamp;
    private String message;
    private String code;
}
