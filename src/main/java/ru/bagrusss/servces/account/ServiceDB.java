package ru.bagrusss.servces.account;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bagrusss.game.ResultsGame;
import ru.bagrusss.servces.database.dao.AdminDAO;
import ru.bagrusss.servces.database.dao.ScoreDAO;
import ru.bagrusss.servces.database.dao.UserDAO;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;
import ru.bagrusss.servces.database.dataset.UserDataSet;
import ru.bagrusss.servces.database.executor.Executor;

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
    private final String className = getClass().getName();
    private final Map<String, UserDataSet> mSessions = new HashMap<>();

    public static final byte DB_CONFIGS_ERROR = 4;
    private final Logger log = Logger.getLogger(getClass().getCanonicalName());

    public ServiceDB(String path) {
        try {
            mUserDAO = new UserDAO(new Executor(path));
            mUserDAO.createUserTable();
            mAdminDAO = new AdminDAO(new Executor(path));
            mAdminDAO.createAdminTable();
            mScoreDAO = new ScoreDAO(new Executor(path));
            mScoreDAO.createScoreTable();
            log.log(Level.INFO, className + " successfully initialized!");
        } catch (SQLException e) {
            log.log(Level.SEVERE, className, e);
            System.exit(DB_CONFIGS_ERROR);
        }
    }

    /**
     * used for test only!
     */
    @Deprecated
    @Override
    public void removeAll() {
        try {
            mUserDAO.deleteAll();
            mSessions.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAdmin(String email) {
        try {
            return mAdminDAO.isAdmin(email);
        } catch (SQLException e) {
            log.log(Level.INFO, "user " + email + " is not admin");
            return false;
        }
    }

    @Override
    public long registerUser(@NotNull String email, @NotNull UserDataSet user) {
        try {
            return mUserDAO.insertUser(user);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
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
            log.log(Level.INFO, "user " + email + " not exists!");
            return null;
        }
    }

    @Nullable
    @Override
    public UserDataSet getUser(@NotNull String email, @NotNull String password) {
        try {
            return mUserDAO.getUser(email, password);
        } catch (SQLException e) {
            log.log(Level.INFO, "user " + email + " not exists!");
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
            log.log(Level.SEVERE, className, e);
        }
        return 0;
    }


    @Override
    public boolean saveResults(List<ScoreDAO.Score> results) {
        try {
            return mScoreDAO.saveResults(results);
        } catch (SQLException e) {
            log.log(Level.SEVERE, className, e);
            return false;
        }
    }

    @Override
    public ScoreDataSet getInfo(long userId) {
        try {
            return mScoreDAO.getUserScore(userId);
        } catch (SQLException e) {
            log.log(Level.SEVERE, className, e);
            return new ScoreDataSet(userId, 0, 0, 0);
        }
    }

    @Nullable
    @Override
    public List<ScoreDataSet> getBestGamers(long count) {
        try {
            return mScoreDAO.getBest(count);
        } catch (SQLException e) {
            log.log(Level.SEVERE, className, e);
            return null;
        }
    }
}
