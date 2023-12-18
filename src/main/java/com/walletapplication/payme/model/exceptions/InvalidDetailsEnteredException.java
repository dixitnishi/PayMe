package com.walletapplication.payme.model.exceptions;

import java.time.LocalDateTime;

public class InvalidDetailsEnteredException extends GlobalWalletException{
    public InvalidDetailsEnteredException(String message){
        super(message, LocalDateTime.now(),GlobalErrorCode.INVALID_EMAIL_SIGNUP);
    }
}
