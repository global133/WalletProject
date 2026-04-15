package com.walletproject.exeptions;

public class WalletProcessingException extends RuntimeException {

    public WalletProcessingException(String message) {
        super(message);
    }
}
