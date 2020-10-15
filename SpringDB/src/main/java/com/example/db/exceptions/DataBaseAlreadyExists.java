package com.example.db.exceptions;

public class DataBaseAlreadyExists extends RuntimeException{
    public DataBaseAlreadyExists(String name) {
        super("Database with name " + name + " already exists");
    }
}
