package com.astonrest.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterValidator {
    private static ParameterValidator instance = new ParameterValidator();

    private ParameterValidator() {

    }

    public static ParameterValidator getInstance() {
        return instance;
    }


    public boolean validateDate(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParameterPattern.DATE_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }


    public boolean validateNumber(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParameterPattern.NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    public boolean validateNameOrSurname(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParameterPattern.NAME_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    public boolean validatePassword(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParameterPattern.PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    public boolean validateEmail(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParameterPattern.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }
}
