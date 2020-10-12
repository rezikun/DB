package com.example.db.entities.types;

public class WrongTypeException extends RuntimeException{
    public WrongTypeException(TypeName name) {
        super("Provided data with wrong type. Needed " + name);
    }
}
