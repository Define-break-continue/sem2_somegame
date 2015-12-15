package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by vladislav.
 */

public class UserProfile {

    private String mUserPassword;
    private String mUserLogin;
    private String mUserEmail;
    private String mFirstName;
    private String mLastName;

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        mUserLogin = login;
        mUserEmail = email;
        mUserPassword = password;
    }

    public UserProfile(String mUserPassword, String mUserLogin, String mUserEmail,
                       @Nullable String mFirstName, @Nullable String mLastName) {
        this.mUserPassword = mUserPassword;
        this.mUserLogin = mUserLogin;
        this.mUserEmail = mUserEmail;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
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

