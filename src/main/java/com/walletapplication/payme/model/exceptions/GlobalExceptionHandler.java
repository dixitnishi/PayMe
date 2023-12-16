package com.walletapplication.payme.model.exceptions;


import com.walletapplication.payme.model.outbound.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalWalletException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalException(GlobalWalletException globalWalletException, Locale locale){
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .code(globalWalletException.getCode())
                        .message(globalWalletException.getMessage())
                        .timeStamp(globalWalletException.getTimeStamp())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleException(Exception e, Locale locale) {
        return ResponseEntity
                .badRequest()
                .body("Exception occur inside API " + e);
    }


}
