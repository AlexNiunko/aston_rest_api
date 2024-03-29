package com.aston_rest_api.controller.dto;

import java.util.Map;

public class UserDto extends AbstractDto {
    private String login;
    private String password;
    private String name;
    private String surname;
    private int usersRole;

    private Map<Long,ProductDto> purchases;

    private UserDto(UserDtoBuilder builder) {
        super(builder.userId);
        this.login = builder.login;
        this.password = builder.password;
        this.name = builder.name;
        this.surname = builder.surname;
        this.usersRole = builder.usersRole;
        this.purchases=builder.purchases;

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getUsersRole() {
        return usersRole;
    }

    public void setUsersRole(int usersRole) {
        this.usersRole = usersRole;
    }

    public Map<Long, ProductDto> getPurchases() {
        return purchases;
    }

    public void setPurchases(Map<Long, ProductDto> purchases) {
        this.purchases = purchases;
    }

    public static class UserDtoBuilder {
        private long userId;
        private String login;
        private String password;
        private String name;
        private String surname;
        private int usersRole;
        private Map<Long,ProductDto> purchases;

        public UserDtoBuilder(long userId) {
            this.userId = userId;
        }

        public UserDtoBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public UserDtoBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserDtoBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserDtoBuilder setUsersRole(int role) {
            this.usersRole = role;
            return this;
        }
        public UserDtoBuilder setPurchases(Map<Long,ProductDto>purchases) {
            this.purchases = purchases;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }



    }
}
