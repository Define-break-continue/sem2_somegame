package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by vladislav
 */

public class DataBaseAccountServiceImpl implements AccountService {

    @Override
    public void removeAll() {

    }

    @Override
    public boolean isAdmin(String sessionId) {
        return false;
    }

    @Override
    public boolean addUser(@NotNull String userName, @NotNull UserProfile userProfile) {
        return false;
    }

    @Override
    public boolean addSession(@NotNull String sessionId, @NotNull UserProfile userProfile) {
        return false;
    }

    @Override
    public boolean removeSession(@NotNull String sessionId) {
        return false;
    }

    @Nullable
    @Override
    public UserProfile getUser(@NotNull String userName) {
        return null;
    }

    @Nullable
    @Override
    public UserProfile getSession(@NotNull String sessionId) {
        return null;
    }

    @Override
    public long getCountActivatedUsers() {
        return 0;
    }

    @Override
    public long getCountRegisteredUsers() {
        return 0;
    }
}
