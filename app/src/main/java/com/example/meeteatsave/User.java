package com.example.meeteatsave;

public class User {

    private String userId;
    private String userName;
    private String userMail;
    private String surname;

    public User() {
    }

    public User(String userId, String userName, String userMail, String surname ) {
        this.userId = userId;
        this.userName = userName;
        this.userMail = userMail;
        this.surname = surname;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
