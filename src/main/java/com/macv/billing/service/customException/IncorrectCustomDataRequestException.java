package com.macv.billing.service.customException;

public class IncorrectCustomDataRequestException extends RuntimeException{
    public IncorrectCustomDataRequestException(String errorMessage) {
        super(errorMessage);
    }
}
