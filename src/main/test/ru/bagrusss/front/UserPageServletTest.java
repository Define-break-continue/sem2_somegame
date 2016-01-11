package ru.bagrusss.front;

import org.junit.Test;
import org.mockito.Mockito;
import ru.bagrusss.servces.account.AccountServiceFake;
import ru.bagrusss.servces.account.UserProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by vladislav on 15.12.15.
 */
public class UserPageServletTest {

    final AccountServiceFake accounts = mock(AccountServiceFake.class, RETURNS_DEEP_STUBS);
    final HttpServletRequest request = Mockito.mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
    final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @Test
    public void testDoGet() throws Exception {
        try (final StringWriter writer = new StringWriter()) {
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            UserProfile user = new UserProfile("bagrusss", "somepass", "bagrusss@gmail.com");
            when(request.getSession().getId()).thenReturn("somesession");
            when(accounts.getSession("somesession")).thenReturn(user).thenReturn(null);
            UserPageServlet userPageServlet = new UserPageServlet(accounts);
            userPageServlet.doGet(request, response);
            assertTrue(writer.toString().contains("bagrusss"));
            writer.flush();
            userPageServlet.doGet(request, response);
            assertTrue(writer.toString().contains("Guest"));
        }
    }
}