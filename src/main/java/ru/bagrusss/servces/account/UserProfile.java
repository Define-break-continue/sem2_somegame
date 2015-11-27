package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;

/**
 * Created by vladislav.
 */

public class UserProfile {
    @NotNull
    private final String mUserPassword;
    @NotNull
    private final String mUserLogin;
    @NotNull
    private final String mUserEmail;

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        mUserLogin = login;
        mUserEmail = email;
        mUserPassword = password;
    }

    @NotNull
    public String getmUserEmail() {
        return mUserEmail;
    }


    @NotNull
    public String getmUserPassword() {
        return mUserPassword;
    }


    @NotNull
    public String getmUserLogin() {
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

