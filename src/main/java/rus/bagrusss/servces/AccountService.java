package rus.bagrusss.servces;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rus.bagrusss.models.UserProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


public class AccountService {

    private static AccountService s_instance;
    private final Map<String, UserProfile> mUsers;
    private final Map<String, UserProfile> mSessions;

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

    public void removeSession(String sessionId){
        mSessions.remove(sessionId);
    }

    @Nullable
    public UserProfile getUser(String userName) {
        return mUsers.get(userName);
    }

    @Nullable
    public UserProfile getSession(String sessionId) {
        return mSessions.get(sessionId);
    }



    public long getCountActivatedUsers() {
        return mSessions.size();
    }

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
