package com.walletapplication.payme.model.exceptions;

public class GlobalErrorCode {
    public static final String ERROR_WALLET_NOT_FOUND = "WALLET_DOES_NOT_EXIST - 1000";
    public static final String INSUFFICIENT_BALANCE = "WALLET_CORE_SERVICE - 1001";
    public static final String INVALID_AMOUNT = "WALLET_CORE_SERVICE - 1002";
    public static final String INVALID_EMAIL = "EMAIL_ALREADY_ASSOCIATED_WITH_ACCOUNT_TRY_NEW_EMAIL - 1003";
    public static final String INVALID_EMAIL_SIGNUP = "1004";
    public static final String OBJECT_CONVERSION_ERROR = "1005";

}
