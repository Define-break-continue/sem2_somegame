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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminDataSet adminDataSet = (AdminDataSet) o;

        return isActivated == adminDataSet.isActivated && email.equals(adminDataSet.email);

    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = result << 6 + (isActivated ? 1 : 0);
        return result;
    }
}
