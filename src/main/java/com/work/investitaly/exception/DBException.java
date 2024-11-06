package com.work.investitaly.exception;

public class DBException extends Exception {

    private String message;

    public DBException(String message) {
        super(message);
    }

}
