package com.astonrest.validator;

public class ParameterPattern {

    public static final String DATE_PATTERN = "(20[0-9][0-9])-?(0[1-9]|1[012])-?(0[1-9]|[12][0-9]|3[01])";
    public static final String NAME_PATTERN = "^[A-Za-zА-ЯЁа-яё]{3,20}$";
    public static final String PASSWORD_PATTERN = "^[A-Za-zА-ЯЁа-яё\\d_!@#,\\.]{6,16}$";
    public static final String EMAIL_PATTERN = "[A-Za-z0-9!#$%&'*+-/=?\\^_`\\{\\}~\\|&&[^\\.\")(,:;<>@ \\[\\]\\/\\s]]((\\.?[A-Za-z0-9!#$%&'*+-/=?\\^_`\\{\\}~|&&[^^\\.\")(,:;<>@ \\[ \\] \\ / \\s]]{1,10}){1,6}||([A-Za-z0-9!#$%&'*+-/=?\\^_`\\{\\}~|&&[^^\\.\")(,:;<>@ \\[ \\] \\ / \\s]]{1,61}))@\\w+\\.\\p{Lower}{2,4}";
    public static final String NUMBER_PATTERN = "^[-+]?[0-9]*[.,]?[0-9]+(?:[eE][-+]?[0-9]+)?$";

    private ParameterPattern() {
    }
}
