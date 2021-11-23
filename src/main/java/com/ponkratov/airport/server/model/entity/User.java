package com.ponkratov.airport.server.model.entity;

import java.io.Serial;
import java.io.Serializable;

public class User implements Entity, Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private final int userID;
    private final String login;
    private final String password;
    private final String email;
    private final int roleID;

    private User(int userID, String login, String password, String email, int roleID) {
        this.userID = userID;
        this.login = login;
        this.password = password;
        this.email = email;
        this.roleID = roleID;
    }

    public int getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getRoleID() {
        return roleID;
    }

    public static class UserBuilder {
        private int userID;
        private String login;
        private String password;
        private String email;
        private int roleID;

        public UserBuilder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public UserBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setRoleID(int roleID) {
            this.roleID = roleID;
            return this;
        }

        public User createUser() {
            return new User(userID, login, password, email, roleID);
        }
    }
}
