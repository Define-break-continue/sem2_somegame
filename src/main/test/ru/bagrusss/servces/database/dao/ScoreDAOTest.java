package ru.bagrusss.servces.database.dao;

import base.TestsWithDB;
import org.junit.Test;
import ru.bagrusss.servces.database.dataset.ScoreDataSet;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vladislav on 21.12.15.
 */
public class ScoreDAOTest extends TestsWithDB {

    private final ScoreDAO mScoreDAO = new ScoreDAO(executor);
    private final UserDAO mUserDAO = new UserDAO(executor);

    @Test
    public void testGetUserScore() throws SQLException {
        assertFalse(mScoreDAO.updateUserScore(1, true, 9));
        assertFalse(mScoreDAO.updateUserScore(-2, true, -10));
        assertNull(mScoreDAO.getBest(-1));
        assertNotNull(mScoreDAO.getBest(2));
        long id1 = mUserDAO.insertUser(new UserDataSet("test@mail.ru", "sss"));
        long id2 = mUserDAO.insertUser(new UserDataSet("test2@mail.ru", "ssssss"));
        ScoreDataSet score = new ScoreDataSet(id1, 0, 0, 0);
        assertEquals(score, mScoreDAO.getUserScore(id1));
        assertEquals(score, mScoreDAO.getUserScore(id2));
        mScoreDAO.updateUserScore(id1, true, 5);
        ScoreDataSet score1 = new ScoreDataSet(id1, 1, 1, 5);
        assertEquals(score1, mScoreDAO.getUserScore(id1));
        List<ScoreDAO.Score> scores = new ArrayList<>(2);
        scores.add(new ScoreDAO.Score(id1, true, 3));
        scores.add(new ScoreDAO.Score(id2, false, 2));
        assertTrue(mScoreDAO.saveResults(scores));
        assertEquals(mScoreDAO.getBest(2).size(), 2);
    }

}