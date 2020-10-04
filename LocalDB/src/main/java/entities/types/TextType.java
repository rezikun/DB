package entities.types;

import helpers.StorageHelper;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextType implements Type, Serializable, Comparable<TextType> {
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
    public void setData(Object data) {
        if (!data.getClass().equals(File.class)) {
            throw new WrongTypeException(this.getName());
        }
        // save file to files folder
        File file = (File) data;
        this.pathToFile = StorageHelper.saveTxtFile(file);
        this.file = file;
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
    public int compareTo(TextType o) {
        return this.file.compareTo(o.file);
    }
}
