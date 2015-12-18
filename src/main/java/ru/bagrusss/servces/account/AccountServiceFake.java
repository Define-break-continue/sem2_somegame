package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;


public final class AccountServiceFake implements AccountService {

    private final Map<String, UserProfile> mUsers;
    private final Map<String, UserProfile> mSessions;

    public AccountServiceFake() {
        mUsers = new HashMap<>();
        mSessions = new HashMap<>();
    }

    @Override
    public void removeAll() {
        mSessions.clear();
        mUsers.clear();
    }

    @Override
    public boolean isAdmin(String email) {
        return false;
    }

    @Override
    public long registerUser(@NotNull String email, @NotNull UserProfile userProfile) {
        if (mUsers.containsKey(email))
            return 0;
        mUsers.put(email, userProfile);
        return userProfile.hashCode();
    }

    @Override
    public boolean addSession(@NotNull String sessionId, @NotNull UserProfile userProfile) {
        return mSessions.put(sessionId, userProfile) == null;
    }

    @Override
    public boolean removeSession(@NotNull String sessionId) {
        return mSessions.remove(sessionId) != null;
    }

    @Override
    public UserProfile getUser(@NotNull String userName) {
        return mUsers.get(userName);
    }

    @Override
    @Nullable
    public UserProfile getSession(@NotNull String sessionId) {
        return mSessions.get(sessionId);
    }

    @Override
    public long getCountActivatedUsers() {
        return mSessions.size();
    }

    @Override
    public long getCountRegisteredUsers() {
        return mUsers.size();
    }


}
