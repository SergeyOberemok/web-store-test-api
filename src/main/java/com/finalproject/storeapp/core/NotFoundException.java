package com.finalproject.storeapp.core;

public class NotFoundException extends Exception {
    public NotFoundException() {
        super("NOT_FOUND");
    }
}
