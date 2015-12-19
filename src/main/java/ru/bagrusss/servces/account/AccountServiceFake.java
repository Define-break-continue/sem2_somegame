package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public final class AccountServiceFake implements AccountService {

    private final Map<String, UserDataSet> mUsers;
    private final Map<String, UserDataSet> mSessions;

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
    public long registerUser(@NotNull String email, @NotNull UserDataSet user) {
        if (mUsers.containsKey(email))
            return 0;
        mUsers.put(email, user);
        return user.hashCode();
    }

    @Override
    public boolean addSession(@NotNull String sessionId, @NotNull UserDataSet user) {
        return mSessions.put(sessionId, user) == null;
    }

    @Override
    public boolean removeSession(@NotNull String sessionId) {
        return mSessions.remove(sessionId) != null;
    }

    @Override
    public UserDataSet getUser(@NotNull String email) {
        return mUsers.get(email);
    }

    @Override
    public UserDataSet getUser(@NotNull String email, @NotNull String password) {
        return null;
    }

    @Override
    @Nullable
    public UserDataSet getSession(@NotNull String sessionId) {
        return mSessions.get(sessionId);
    }

    @Override
    public int getCountActivatedUsers() {
        return mSessions.size();
    }

    @Override
    public long getCountRegisteredUsers() {
        return mUsers.size();
    }


}
