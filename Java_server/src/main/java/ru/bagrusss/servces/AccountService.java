package ru.bagrusss.servces;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.models.UserProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


public final class AccountService implements AccountServiceInterface {

    private static AccountService s_instance;
    private final Map<String, UserProfile> mUsers;
    private final Map<String, UserProfile> mSessions;

    public static AccountService getInstance() {
        AccountService localInstance = s_instance;
        if (localInstance == null) {
            synchronized (AccountService.class) {
                localInstance = s_instance;
                if (localInstance == null) s_instance = localInstance = new AccountService();
            }
        }
        return localInstance;
    }

    private AccountService() {
        mUsers = new HashMap<>();
        mSessions = new HashMap<>();
    }

    @Override
    public boolean addUser(String userName, UserProfile userProfile) {
        if (mUsers.containsKey(userName))
            return false;
        mUsers.put(userName, userProfile);
        return true;
    }

    @Override
    public void addSession(String sessionId, UserProfile userProfile) {
        mSessions.put(sessionId, userProfile);
    }

    @Override
    public void removeSession(String sessionId){
        mSessions.remove(sessionId);
    }

    @Override
    @Nullable
    public UserProfile getUser(String userName) {
        return mUsers.get(userName);
    }

    @Override
    @Nullable
    public UserProfile getSession(String sessionId) {
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

    public void doSaveUser(@NotNull HttpServletRequest request, @NotNull UserProfile user){
        HttpSession session = request.getSession();
        @NotNull
        String userId = session.getId();
        addSession(userId, user);
    }
}
