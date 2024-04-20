package com.aston_rest_api.controller.dto;

import java.util.Map;
import java.util.Objects;

public class UserDto {
    private long idUser;
    private String login;
    private String password;
    private String name;
    private String surname;
    private int isAdmin;

    private UserDto(UserDtoBuilder builder) {
        this.idUser = builder.idUser;
        this.login = builder.login;
        this.password = builder.password;
        this.name = builder.name;
        this.surname = builder.surname;
        this.isAdmin = builder.isAdmin;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
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

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return getIdUser() == userDto.getIdUser() && getIsAdmin() == userDto.getIsAdmin() && getLogin().equals(userDto.getLogin()) && getPassword().equals(userDto.getPassword()) && getName().equals(userDto.getName()) && getSurname().equals(userDto.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUser(), getLogin(), getPassword(), getName(), getSurname(), getIsAdmin());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("idUser=").append(idUser);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", isAdmin=").append(isAdmin);
        sb.append('}');
        return sb.toString();
    }

    public static class UserDtoBuilder {
        private long idUser;
        private String login;
        private String password;
        private String name;
        private String surname;
        private int isAdmin;

        public UserDtoBuilder(long idUser) {
            this.idUser = idUser;
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

        public UserDtoBuilder setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }


        public UserDto build() {
            return new UserDto(this);
        }


    }


}
