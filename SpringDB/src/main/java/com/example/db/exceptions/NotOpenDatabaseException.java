package com.example.db.exceptions;

public class NotOpenDatabaseException extends RuntimeException{
    public NotOpenDatabaseException(){
        super("No database was chosen");
    }
}
