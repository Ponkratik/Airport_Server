package com.ponkratov.airport.server.model.entity;

import java.util.Objects;

public class User implements Entity{
    private final int userID;
    private final String login;
    private final String email;
    private final String lastName;
    private final String firstName;
    private final String surName;
    private final String userPictureLink;
    private final boolean isBlocked;
    private final int roleID;

    public User(int userID, String login, String email, String lastName, String firstName, String surName, String userPictureLink, boolean isBlocked, int roleID) {
        this.userID = userID;
        this.login = login;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.surName = surName;
        this.userPictureLink = userPictureLink;
        this.isBlocked = isBlocked;
        this.roleID = roleID;
    }

    public int getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public String getUserPictureLink() {
        return userPictureLink;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public int getRoleID() {
        return roleID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return userID == user.userID
                && roleID == user.roleID
                && isBlocked == user.isBlocked
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(surName, user.surName)
                && Objects.equals(userPictureLink, user.userPictureLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, login, email, lastName, firstName, surName, userPictureLink, isBlocked, roleID);
    }

    public static class UserBuilder {
        private int userID;
        private String login;
        private String email;
        private String lastName;
        private String firstName;
        private String surName;
        private String userPictureLink;
        private boolean isBlocked;
        private int roleID;

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setSurName(String surName) {
            this.surName = surName;
        }

        public void setUserPictureLink(String userPictureLink) {
            this.userPictureLink = userPictureLink;
        }

        public void setBlocked(boolean blocked) {
            isBlocked = blocked;
        }

        public void setRoleID(int roleID) {
            this.roleID = roleID;
        }

        public User createUser() {
            return new User(userID, login, email, lastName, firstName, surName, userPictureLink, isBlocked, roleID);
        }
    }
}
