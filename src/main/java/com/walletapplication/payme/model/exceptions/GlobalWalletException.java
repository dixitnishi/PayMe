package com.walletapplication.payme.model.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalWalletException extends RuntimeException {

    private String message;
    private LocalDateTime timeStamp;
    private String code;

    public GlobalWalletException(String message) {
        super(message);
    }
}
