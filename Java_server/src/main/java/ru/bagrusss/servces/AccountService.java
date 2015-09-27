package ru.bagrusss.servces;

import org.jetbrains.annotations.Nullable;
import ru.bagrusss.models.UserProfile;

import java.util.HashMap;
import java.util.Map;


public class AccountService {

    private static AccountService s_instance;
    private Map<String, UserProfile> mUsers;
    private Map<String, UserProfile> mSessions;

    public static AccountService getInstance() {
        AccountService localInstance = s_instance;
        if (localInstance == null) {
            synchronized (AccountService.class) {
                localInstance = s_instance;
                if (localInstance == null) {
                    s_instance = localInstance = new AccountService();
                }
            }
        }
        return localInstance;
    }

    public AccountService() {
        mUsers = new HashMap<>();
        mSessions = new HashMap<>();
    }

    public boolean addUser(String userName, UserProfile userProfile) {
        if (mUsers.containsKey(userName))
            return false;
        mUsers.put(userName, userProfile);
        return true;
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        mSessions.put(sessionId, userProfile);
    }

    @Nullable
    public UserProfile getUser(String userName) {
        return mUsers.get(userName);
    }

    @Nullable
    public UserProfile getSessions(String sessionId) {
        return mSessions.get(sessionId);
    }

    public long getCountActivatedUsers(){
        return mUsers.size();
    }

    public long getCountRegisteredUsers(){
        return mSessions.size();
    }
}
