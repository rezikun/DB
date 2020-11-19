package entities.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EmailType implements Type, Serializable {

    private String data;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean equals(Object obj) {
        EmailType s = (EmailType) obj;
        return data.equals(s.data);
    }

    @Override
    public TypeName getName() {
        return TypeName.EMAIL;
    }

    @Override
    public Type setData(Object data) {
        if (data.getClass().equals(String.class)) {
            String check = (String) data;
            if (isValid(check)) {
                this.data = check;
                return this;
            }
        }
        throw new WrongTypeException(this.getName());
    }

    private boolean isValid(String data) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(data);
        return matcher.matches();
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
    public int compareTo(Type o) { // compare by interval length
        EmailType t = (EmailType) o;
        return this.data.compareTo(t.data);
    }

}
