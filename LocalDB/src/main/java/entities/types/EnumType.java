package entities.types;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumType implements Type, Serializable {
    private String data;
    private Pattern VALID_ENUM_REGEX;

    public EnumType() {}

    public EnumType(List<String> possibleValues) {
        StringBuilder pattern = new StringBuilder();
        for (String enumValue : possibleValues) {
            pattern.append(enumValue).append("|");
        }
        pattern.deleteCharAt(pattern.length() - 1);
        VALID_ENUM_REGEX = Pattern.compile(pattern.toString());
    }

    @Override
    public boolean equals(Object obj) {
        EnumType s = (EnumType) obj;
        return data.equals(s.data);
    }

    @Override
    public TypeName getName() {
        return TypeName.ENUM;
    }

    @Override
    public Type setData(Object data) {
        if (data instanceof String) {
            String s = (String) data;
            if (isValid(s)) {
                this.data = s;
                return this;
            }
        }
        throw new WrongTypeException(this.getName());
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public Class getViewClass() {
        return String.class;
    }

    @Override
    public int compareTo(Type o) {
        EnumType t = (EnumType) o;
        return this.data.compareTo(t.data);
    }
    private boolean isValid(String data) {
        Matcher matcher = VALID_ENUM_REGEX.matcher(data);
        return matcher.matches();
    }
}
