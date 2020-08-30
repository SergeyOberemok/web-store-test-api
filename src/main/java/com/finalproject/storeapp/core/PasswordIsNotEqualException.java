package com.finalproject.storeapp.core;

public class PasswordIsNotEqualException extends Exception {
    public PasswordIsNotEqualException() {
        super("PASSWORD_IS_NOT_EQUAL");
    }
}
