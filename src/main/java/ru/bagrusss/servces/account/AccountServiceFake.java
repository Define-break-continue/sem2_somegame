package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public final class AccountServiceFake implements AccountServiceInterface {

    private static AccountServiceFake mInstance;
    private final Map<String, UserProfile> mUsers;
    private final Map<String, UserProfile> mSessions;

    public static AccountServiceFake getInstance() {
        AccountServiceFake localInstance = mInstance;
        if (localInstance == null) {
            synchronized (AccountServiceFake.class) {
                localInstance = mInstance;
                if (localInstance == null) mInstance = localInstance = new AccountServiceFake();
            }
        }
        return localInstance;
    }

    private AccountServiceFake() {
        mUsers = new HashMap<>();
        mSessions = new HashMap<>();
    }

    @Override
    public void removeAll() {
        mSessions.clear();
        mUsers.clear();
    }

    @Override
    public boolean isAdmin(String sessionId) {
        return false;
    }

    @Override
    public boolean addUser(@NotNull String userName, @NotNull UserProfile userProfile) {
        if (mUsers.containsKey(userName))
            return false;
        mUsers.put(userName, userProfile);
        return true;
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

    public void doSaveUser(@NotNull HttpServletRequest request, @NotNull UserProfile user) {
        addSession(request.getSession().getId(), user);
    }


}
