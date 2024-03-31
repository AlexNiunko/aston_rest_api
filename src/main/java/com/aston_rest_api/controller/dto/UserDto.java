package com.aston_rest_api.controller.dto;

import java.util.Map;

public class UserDto extends AbstractDto {
    private String login;
    private byte [] password;
    private String name;
    private String surname;
    private boolean isAdmin;
    private Map<Long, ProductDto>purchases;

}
