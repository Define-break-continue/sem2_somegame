package ru.bagrusss.servces.database.dataset;

import org.jetbrains.annotations.NotNull;

/**
 * Created by vladislav
 */

@SuppressWarnings("unused")
public class AdminDataSet {
    String email;
    boolean isActivated;

    public AdminDataSet(@NotNull String email, boolean isActivated) {
        this.email = email;
        this.isActivated = isActivated;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return isActivated;
    }
}
