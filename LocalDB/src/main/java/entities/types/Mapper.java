package entities.types;

import entities.Column;

public class Mapper {
    public static Type typeNameToType(TypeName name) {
        switch (name) {
            case CHAR:
                return new CharType();
            case INT:
                return new IntType();
            case REAL:
                return new RealType();
            case TEXT:
                return new TextType();
            case STRING:
                return new StringType();
            case INT_INTERVAL:
                return new IntIntervalType();
            default:
                throw new RuntimeException("This type is not supported");
        }
    }
    public static TypeName toType(String type) {
        if (type.toLowerCase().equals("int")) {
            return TypeName.INT;
        }
        if (type.toLowerCase().equals("string")) {
            return TypeName.STRING;
        }
        if (type.toLowerCase().equals("real")) {
            return TypeName.REAL;
        }
        if (type.toLowerCase().equals("char")) {
            return TypeName.CHAR;
        }
        if (type.toLowerCase().equals("text") || type.toLowerCase().equals("file")) {
            return TypeName.TEXT;
        }
        if (type.toLowerCase().equals("interval")) {
            return TypeName.INT_INTERVAL;
        }
        throw new RuntimeException("No such type: " + type);
    }
}
