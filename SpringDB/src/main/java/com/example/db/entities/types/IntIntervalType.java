package com.example.db.entities.types;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class IntIntervalType implements Type, Serializable {
    @Data
    @AllArgsConstructor
    class Interval implements Serializable{
        private Integer min;
        private Integer max;
    }

    private Interval data;

    public IntIntervalType() {
        this.data = new Interval(0, 0);
    }

    @Override
    public TypeName getName() {
        return TypeName.INT_INTERVAL;
    }

    @Override
    public Type setData(Object data) {
        if (data.getClass().equals(Interval.class)) {
            var val = (Interval) data;
            if (val.min > val.max) {
                throw new RuntimeException("Minimum can`t be bigger than maximum");
            }
            this.data = val;
            return this;

        }
        if (data.getClass().equals(String.class)) {
            String check = (String) data;
            if (isValid(check)) {
                this.data = this.fromString(check);
                return this;
            }
        }
        throw new WrongTypeException(this.getName());
    }

    private boolean isValid(String data) {
        Pattern pattern = Pattern.compile("\\[?\\d+[ .,]\\d+\\]?");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    private Interval fromString(String data) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(data);
        List<String> allMatches = new ArrayList<>();
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        if (allMatches.size() != 1) {
            throw new RuntimeException("Wrong interval");
        }
        var result = allMatches.stream().map(Integer::parseInt).collect(Collectors.toList());
        if (result.get(0) > result.get(1)) {
            throw new RuntimeException("Minimum can`t be bigger than maximum");
        }
        return new Interval(result.get(0), result.get(1));
    }

    @Override
    public String getData() {
        return "[" + data.min.toString() + ", " + data.max.toString() + "]";
    }

    @Override
    public Class getViewClass() {
        return String.class;
    }

    @Override
    public int compareTo(Type o) { // compare by interval length
        IntIntervalType t = (IntIntervalType) o;
        Integer length1 = this.data.max - this.data.min;
        Integer length2 = t.data.max - this.data.min;
        return length1.compareTo(length2);
    }


}
