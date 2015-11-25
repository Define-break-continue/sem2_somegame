package ru.bagrusss.servces;

import org.jetbrains.annotations.Nullable;
import ru.bagrusss.models.UserProfile;

/**
 * Created by vladislav on 12.10.15.
 */
public interface AccountServiceInterface {

    boolean addUser(String userName, UserProfile userProfile);

    void addSession(String sessionId, UserProfile userProfile);

    void removeSession(String sessionId);

    @Nullable
    UserProfile getUser(String userName);

    @Nullable
    UserProfile getSession(String sessionId);

    long getCountActivatedUsers();

    long getCountRegisteredUsers();
}
