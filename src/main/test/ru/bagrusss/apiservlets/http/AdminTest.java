package ru.bagrusss.apiservlets.http;

import org.junit.Test;
import org.mockito.Mockito;
import ru.bagrusss.main.Context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Permission;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

/**
 * Created by vladislav on 13.01.16.
 */
public class AdminTest {

    final HttpServletRequest request = Mockito.mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
    final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @Test
    public void testDoGet() throws IOException, ServletException {
        when(request.getParameter(Admin.SHUTDOWN))
                .thenReturn("1000")
                .thenReturn("abs")
                .thenReturn(null);
        try (ByteArrayOutputStream outContent = new ByteArrayOutputStream()) {
            PrintStream old = System.out;
            System.setOut(new PrintStream(outContent));
            Admin adminPageServlet = new Admin(new Context());
            SecurityManager oldManager = System.getSecurityManager();
            System.setSecurityManager(new NoExitSecurityManager());
            try {
                adminPageServlet.doGet(request, response);
            } catch (ExitException e) {
                assertEquals(e.status, 0);
            }
            adminPageServlet.doGet(request, response);
            assertTrue(outContent.toString().contains(Admin.STATUS_STOP_ERROR));
            outContent.flush();
            adminPageServlet.doGet(request, response);
            assertTrue(outContent.toString().contains(Admin.STATUS_RUN));
            System.setOut(old);
            System.setSecurityManager(oldManager);
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