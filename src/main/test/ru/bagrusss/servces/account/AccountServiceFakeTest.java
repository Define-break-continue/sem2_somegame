package ru.bagrusss.servces.account;

import org.jetbrains.annotations.TestOnly;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by vladislav
 */

public class AccountServiceFakeTest {

    AccountServiceFake mAccountService;
    List<UserDataSet> mUsers;

    @Before
    public void setUp() throws Exception {
        mAccountService = new AccountServiceFake();
        mUsers = new LinkedList<>();
        mUsers.add(new UserDataSet("vlad@mail.ru", "123"));
        mUsers.add(new UserDataSet("vlad1@mail.ru", "1232"));
        mUsers.add(new UserDataSet("vlad2@mail.ru", "some"));
        mUsers.add(new UserDataSet("vlad4@mail.ru", "456"));
    }

    @After
    public void tearDown() throws Exception {
        mAccountService.removeAll();
        mUsers.clear();
    }

    @TestOnly
    @Test
    public void testAddUser() throws Exception {
        for (UserDataSet mUser : mUsers) {
            mAccountService.registerUser(mUser.getEmail(), mUser);
        }
        assertFalse(mAccountService.registerUser("vlad3", new UserDataSet("vlad3@mail.ru", "456")) == 0);
    }

    @TestOnly
    @Test
    public void testGetUser() throws Exception {
        for (UserDataSet usr : mUsers) {
            mAccountService.registerUser(usr.getEmail(), usr);
            assertEquals(mAccountService.getUser(usr.getEmail()), usr);
        }
        assertEquals(null, mAccountService.getUser("vlad5"));
    }

    @TestOnly
    @Test
    public void testAddSession() throws Exception {
        for (UserDataSet usr : mUsers) {
            mAccountService.addSession(usr.toString(), usr);
        }
        assertEquals(4, mAccountService.getCountActivatedUsers());
        assertFalse(mAccountService.addSession(mUsers.get(0).toString(), mUsers.get(0)));
    }

    @TestOnly
    @Test
    public void testGetSession() throws Exception {
        assertNull(mAccountService.getSession(mUsers.get(0).toString()));
        for (UserDataSet usr : mUsers) {
            mAccountService.addSession(usr.toString(), usr);
            assertNotNull(mAccountService.getSession(usr.toString()));
        }
    }

    @TestOnly
    @Test
    public void testRemoveSession() throws Exception {
        for (UserDataSet usr : mUsers) {
            mAccountService.addSession(usr.toString(), usr);
        }
        assertFalse(mAccountService.removeSession(new UserDataSet(" ", " ").toString()));
        for (UserDataSet usr : mUsers) {
            assertTrue(mAccountService.removeSession(usr.toString()));
        }
    }

    @TestOnly
    @Test
    public void testDoSaveUser() throws Exception {
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getId()).thenReturn("somesession");

        HttpServletRequest mock = mock(HttpServletRequest.class);
        when(mock.getSession()).thenReturn(mockSession);
        mAccountService.addSession(mockSession.getId(), mUsers.get(0));
        assertEquals(1, mAccountService.getCountActivatedUsers());
        mAccountService.addSession(mockSession.getId(), mUsers.get(0));
        assertEquals(mAccountService.getCountActivatedUsers(), 1);
    }

    @TestOnly
    @Test
    public void testRemoveAll() throws Exception {
        for (UserDataSet usr : mUsers) {
            mAccountService.registerUser(usr.getEmail(), usr);
            mAccountService.addSession(usr.toString(), usr);
        }
        mAccountService.removeAll();
        assertEquals(0, mAccountService.getCountActivatedUsers());
        assertEquals(0, mAccountService.getCountRegisteredUsers());
    }
}