package com.aston_rest_api.model;

import java.util.Map;

public class User extends AbstractEntity {
    private String login;
    private byte [] password;
    private String name;
    private String surname;
    private boolean isAdmin;
    private Map<Long,Product>purchases;
}
