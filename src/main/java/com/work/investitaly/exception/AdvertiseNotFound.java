package com.work.investitaly.exception;

public class AdvertiseNotFound extends DBException {

    private String message;

    public AdvertiseNotFound(String message) {
        super(message);
    }

}
