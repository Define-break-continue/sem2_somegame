package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;

/**
 * Created by vladislav.
 */

public class UserProfile {

    private final String mUserPassword;
    private final String mUserLogin;
    private final String mUserEmail;

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        mUserLogin = login;
        mUserEmail = email;
        mUserPassword = password;
    }

    @NotNull
    public String getUserEmail() {
        return mUserEmail;
    }


    @NotNull
    public String getUserPassword() {
        return mUserPassword;
    }


    @NotNull
    public String getUserLogin() {
        return mUserLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile some = (UserProfile) o;
        return mUserLogin.equals(some.mUserLogin) && mUserEmail.equals(some.mUserEmail);
    }

    @Override
    public int hashCode() {
        int result = mUserLogin.hashCode();
        result = 31 * result + mUserEmail.hashCode();
        return result;
    }
}

