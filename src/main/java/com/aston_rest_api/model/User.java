package com.aston_rest_api.model;

import java.util.Map;
import java.util.Objects;

public class User extends AbstractEntity {
    private String login;
    private String password;
    private String name;
    private String surname;
    private int usersRole;

    private Map<Long, Product> purchases;

    private User(UserBuilder builder) {
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

    public Map<Long, Product> getPurchases() {
        return purchases;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", usersRole=").append(usersRole);
        sb.append(", purchases=").append(purchases);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    public void setPurchases(Map<Long, Product> purchases) {
        this.purchases = purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUsersRole() == user.getUsersRole() && getLogin().equals(user.getLogin()) && getPassword().equals(user.getPassword()) && getName().equals(user.getName()) && getSurname().equals(user.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getName(), getSurname(), getUsersRole());
    }

    public static class UserBuilder {
        private long userId;
        private String login;
        private String password;
        private String name;
        private String surname;
        private int usersRole;
        private Map<Long, Product> purchases;

        public UserBuilder(long userId) {
            this.userId = userId;
        }
        public UserBuilder() {

        }


        public UserBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder setUsersRole(int role) {
            this.usersRole = role;
            return this;
        }
        public UserBuilder setPurchases(Map<Long, Product>purchases) {
            this.purchases = purchases;
            return this;
        }

        public User build() {
            return new User(this);
        }



    }
}
