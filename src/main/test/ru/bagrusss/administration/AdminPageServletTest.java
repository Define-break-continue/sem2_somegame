package ru.bagrusss.administration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Permission;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

/**
 * Created by vladislav
 */

public class AdminPageServletTest {


    final HttpServletRequest request = Mockito.mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
    final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @Before
    public void setUp() throws Exception {
        System.setSecurityManager(new NoExitSecurityManager());
    }

    @After
    public void tearDown() throws Exception {
        System.setSecurityManager(null);
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        when(request.getParameter("shutdown"))
                .thenReturn("1000")
                .thenReturn("abs")
                .thenReturn(null);
        try (final StringWriter writer = new StringWriter()) {
            when(response.getWriter()).thenReturn(new PrintWriter(writer));
            AdminPageServlet adminPageServlet = new AdminPageServlet();
            try {
                adminPageServlet.doGet(request, response);
            } catch (ExitException e) {
                assertEquals(e.status, 0);
            }
            writer.flush();
            adminPageServlet.doGet(request, response);
            assertTrue(writer.toString().contains(AdminPageServlet.STATUS_STOP_ERROR))
            ;
            writer.flush();
            adminPageServlet.doGet(request, response);
            assertTrue(writer.toString().contains(AdminPageServlet.STATUS_RUN));
        }
    }

    protected static class ExitException extends SecurityException {
        public final int status;

        public ExitException(int status) {
            super("There is no escape!");
            this.status = status;
        }
    }

    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {

        }

        @Override
        public void checkPermission(Permission perm, Object context) {

        }

        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }
}