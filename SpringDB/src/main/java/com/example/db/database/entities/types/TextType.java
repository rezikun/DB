package com.example.db.database.entities.types;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextType implements Type, Serializable {
    private String pathToFile;
    private File file;

    public TextType() {
        this.pathToFile = "";
    }

    @Override
    public TypeName getName() {
        return TypeName.TEXT;
    }

    @Override
    public Type setData(Object data) {
        if (data.getClass().equals(File.class)) {
            // save file to files folder
            File file = (File) data;
            // this.pathToFile = StorageHelper.saveTxtFile(file); //TODO: important to save is somewhere
            this.file = file;
            return this;
        }
        if (data.getClass().equals(String.class)) {
            this.pathToFile = (String) data;
            return this;
        }
        throw new WrongTypeException(this.getName());
    }

    @Override
    public String getData() { // read file and return content
        if (this.pathToFile.isEmpty()) {
            return "No file";
        }
        try {
            return Files.readString(Paths.get(this.pathToFile), StandardCharsets.US_ASCII);
        } catch (Exception e) {
            throw new RuntimeException("Reading file failed");
        }
    }

    @Override
    public Class getViewClass() {
        return String.class;
    }

    @Override
    public int compareTo(Type o) {
        TextType t = (TextType) o;
        return this.file.compareTo(t.file);
    }
}
