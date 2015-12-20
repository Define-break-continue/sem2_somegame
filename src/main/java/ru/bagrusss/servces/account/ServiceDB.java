package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.game.ResultsGame;
import ru.bagrusss.servces.database.dao.AdminDAO;
import ru.bagrusss.servces.database.dao.ScoreDAO;
import ru.bagrusss.servces.database.dao.UserDAO;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vladislav
 */

public final class ServiceDB implements AccountService, ResultsGame {

    private UserDAO mUserDAO;
    private AdminDAO mAdminDAO;
    private ScoreDAO mScoreDAO;

    private final Map<String, UserDataSet> mSessions = new HashMap<>();


    private final Logger dbLogger = Logger.getLogger(getClass().getCanonicalName());

    public ServiceDB() {
        try {
            mUserDAO = new UserDAO();
            mAdminDAO = new AdminDAO();
            mScoreDAO = new ScoreDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * used for test only!
     */
    @Deprecated
    @Override
    public void removeAll() {

    }

    @Override
    public boolean isAdmin(String email) {
        try {
            return mAdminDAO.isAdmin(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long registerUser(@NotNull String email, @NotNull UserDataSet user) {
        try {
            return mUserDAO.insertUser(user);
        } catch (SQLException e) {
            dbLogger.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean addSession(@NotNull String sessionId, @NotNull UserDataSet user) {
        return mSessions.put(sessionId, user) == null;
    }

    @Override
    public boolean removeSession(@NotNull String sessionId) {
        return mSessions.remove(sessionId) != null;
    }

    @Nullable
    @Override
    public UserDataSet getUser(@NotNull String email) {
        try {
            return mUserDAO.getUser(email, null);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    @Override
    public UserDataSet getUser(@NotNull String email, @NotNull String password) {
        try {
            return mUserDAO.getUser(email, password);
        } catch (SQLException e) {
            dbLogger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    @Nullable
    @Override
    public UserDataSet getSession(@NotNull String sessionId) {
        return mSessions.get(sessionId);
    }

    @Override
    public int getCountActivatedUsers() {
        return mSessions.size();
    }

    @Override
    public long getCountRegisteredUsers() {
        try {
            return mUserDAO.getUserCount();
        } catch (SQLException e) {
            dbLogger.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }


    @Override
    public boolean saveResults(List<ScoreDAO.Score> results) {
        try {
            return mScoreDAO.saveResults(results);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Nullable
    @Override
    public ScoreDataSet getInfo(long userId) {
        try {
            return mScoreDAO.getUserScore(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
