package entities.types;

public class Mapper {
    public static Type typeNameToType(TypeName name) {
        switch (name) {
            case CHAR:
                return new CharType();
            case INT:
                return new IntType();
            case REAL:
                return new RealType();
            case ENUM:
                return new EnumType();
            case STRING:
                return new StringType();
            case EMAIL:
                return new EmailType();
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
        if (type.toLowerCase().equals("enum")) {
            return TypeName.ENUM;
        }
        if (type.toLowerCase().equals("email")) {
            return TypeName.EMAIL;
        }
        throw new RuntimeException("No such type: " + type);
    }
}
