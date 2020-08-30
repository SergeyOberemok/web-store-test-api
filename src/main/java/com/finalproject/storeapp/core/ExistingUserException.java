package com.finalproject.storeapp.core;

public class ExistingUserException extends Exception {
    public ExistingUserException() {
        super("EXISTING_USER");
    }
}
