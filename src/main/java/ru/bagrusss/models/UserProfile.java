package ru.bagrusss.models;

/**
 * Created by vladislav.
 */

public class UserProfile {
    private final String userPassword;
    private final String userLogin;
    private final String userEmail;

    public UserProfile(String login, String password, String email) {
        userLogin = login;
        userEmail = email;
        userPassword = password;
    }

    public String getUserEmail() {
        return userEmail;
    }


    public String getUserPassword() {
        return userPassword;
    }


    public String getUserLogin() {
        return userLogin;
    }
}

