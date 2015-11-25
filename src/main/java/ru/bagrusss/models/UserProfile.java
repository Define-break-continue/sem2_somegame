package ru.bagrusss.models;

import org.jetbrains.annotations.NotNull;

/**
 * Created by vladislav.
 */

public class UserProfile {
    @NotNull
    private final String userPassword;
    @NotNull
    private final String userLogin;
    @NotNull
    private final String userEmail;

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        userLogin = login;
        userEmail = email;
        userPassword = password;
    }

    @NotNull
    public String getUserEmail() {
        return userEmail;
    }


    @NotNull
    public String getUserPassword() {
        return userPassword;
    }


    @NotNull
    public String getUserLogin() {
        return userLogin;
    }
}

