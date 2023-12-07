package com.walletapplication.payme.model.outbound;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private LocalDateTime timeStamp;
    private String message;
    private String code;
}
