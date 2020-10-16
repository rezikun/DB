package com.example.db.database.entities.types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
            File file = (File) data;
            this.pathToFile = file.getAbsolutePath();
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

    public static void printContent(File file) throws Exception {
        System.out.println("Print File Content");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
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
