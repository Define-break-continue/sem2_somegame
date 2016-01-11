package ru.bagrusss.front;

import org.jetbrains.annotations.TestOnly;
import org.junit.Test;
import org.mockito.Mockito;
import ru.bagrusss.servces.account.AccountServiceFake;
import ru.bagrusss.servces.account.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

/**
 * Created by vladislav
 */
public class SignInServletTest {

    final AccountServiceFake accounts = AccountServiceFake.getInstance();
    final HttpServletRequest request = Mockito.mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
    final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @Test
    @TestOnly
    public void testDoGet() throws IOException, ServletException {
        when(request.getParameter("logout")).thenReturn("111").thenReturn("something");
        when(request.getSession().getId()).thenReturn("sess");
        SignInServlet signInServlet = new SignInServlet(accounts);
        UserProfile usr = new UserProfile("some", "somepass", "some@mail.ru");
        accounts.addUser("user", usr);
        accounts.addSession("sess", usr);
        try (StringWriter writer = new StringWriter()) {
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            signInServlet.doGet(request, response);
            assertEquals(accounts.getCountActivatedUsers(), 0);
            signInServlet.doGet(request, response);
            assertFalse(accounts.removeSession("something"));
        }
    }

    @Test
    @TestOnly
    public void testDoPost() throws IOException, ServletException {
        when(request.getParameter("name")).thenReturn("bagrusss").thenReturn("bagrusss").thenReturn("www");
        when(request.getParameter("password")).thenReturn("somepass").thenReturn("somepass").thenReturn("sss");
        when(request.getSession().getId()).thenReturn("sess");
        SignInServlet signInServlet = new SignInServlet(accounts);
        UserProfile user = new UserProfile("bagrusss", "somepass", "bagrusss@gmail.com");
        UserProfile user2 = new UserProfile("bag", "some", "bagrusss@mail.ru");
        accounts.addUser("bagrusss", user);
        accounts.addUser("bag", user2);
        try (final StringWriter writer = new StringWriter()) {
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            signInServlet.doPost(request, response);
            assertEquals(accounts.getCountActivatedUsers(), 1);
            signInServlet.doPost(request, response);
            assertEquals(accounts.getCountActivatedUsers(), 1);
            signInServlet.doPost(request, response);
            assertEquals(accounts.getCountActivatedUsers(), 1);
        }
    }

}