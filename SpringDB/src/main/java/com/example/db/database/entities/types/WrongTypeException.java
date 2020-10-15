package com.example.db.database.entities.types;

public class WrongTypeException extends RuntimeException{
    public WrongTypeException(TypeName name) {
        super("Provided data with wrong type. Needed " + name);
    }
}
