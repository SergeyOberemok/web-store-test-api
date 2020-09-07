package com.finalproject.storeapp.core.exceptions;

public class PasswordDoesntMatchException extends Exception {
    public PasswordDoesntMatchException() {
        super("PASSWORD_DOESNT_MATCH");
    }
}
