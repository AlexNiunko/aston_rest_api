package com.aston_rest_api.command;

import com.aston_rest_api.controller.arguments.UserArguments;

public enum UserParameters {
    USER_ID(UserArguments.USER_ID),
    LOGIN(UserArguments.LOGIN),
    PASSWORD(UserArguments.PASSWORD),
    NAME(UserArguments.NAME),
    SURNAME(UserArguments.SURNAME);
    private String parameterName;

    public String getParameterName() {
        return parameterName;
    }
    private UserParameters(String parameterName) {
        this.parameterName=parameterName;
    }
}
