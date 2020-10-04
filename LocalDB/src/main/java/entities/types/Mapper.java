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
}
