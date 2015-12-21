package ru.bagrusss.servces.database.dao;

import base.TestsWithDB;
import org.junit.Test;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by vladislav
 */

public class UserDAOTest extends TestsWithDB {
    private final UserDAO userDAO = new UserDAO(executor);

    @Test(expected = SQLException.class)
    public void testUserDAO() throws SQLException {
        UserDataSet user = new UserDataSet("user@gmail.com", "somepass");
        long id = userDAO.insertUser(user);
        assertEquals(userDAO.insertUser(user), 0);
        assertNotEquals(userDAO.insertUser(new UserDataSet("user2@gmail.com", "somepasswd")), 0);
        assertEquals(userDAO.getUser("user@gmail.com", null), user);
        assertEquals(userDAO.getUserById(id), user);
        assertEquals(userDAO.getUserCount(), 2);
        user.setFirstname("someName");
        user.setLastname("someLastName");
        assertEquals(userDAO.updateUser(user), true);
        UserDataSet tstuser = userDAO.getUserById(id);
        assertEquals(tstuser.getLastname(), tstuser.getLastname());
        assertEquals(tstuser.getFirstname(), tstuser.getFirstname());
        assertEquals(tstuser, userDAO.getUser(user.getEmail(), user.getPassword()));
        userDAO.getUserById(5);
        userDAO.getUser("storm@gmail.com", null);
    }

}