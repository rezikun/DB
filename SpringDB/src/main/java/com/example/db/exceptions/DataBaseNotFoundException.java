package com.example.db.exceptions;

public class DataBaseNotFoundException extends RuntimeException {
    public DataBaseNotFoundException(String name) {
        super("Database not found " + name);
    }
}
