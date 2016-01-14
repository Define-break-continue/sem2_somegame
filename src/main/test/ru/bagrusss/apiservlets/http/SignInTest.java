package ru.bagrusss.apiservlets.http;

import base.BaseServletTest;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import ru.bagrusss.helpers.Errors;
import ru.bagrusss.servces.database.dataset.UserDataSet;

import javax.servlet.ServletException;
import java.io.*;

import static org.mockito.Mockito.when;

/**
 * Created by vladislav
 */

@SuppressWarnings("deprecation")
public class SignInTest extends BaseServletTest {

    final SignIn signIn = new SignIn(CONTEXT);

    @Test
    public void testDoPostUserAuthorized() throws IOException, ServletException {
        try (StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            ACCOUNTSERVICE.addSession("someid", new UserDataSet("email@rm.rf", "pass"));
            when(request.getSession().getId()).thenReturn("someid");
            when(response.getWriter()).thenReturn(writer);
            signIn.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_USER_AUTHORIZED));
        }
    }

    @Test
    public void testDoPostIncorrectJson() throws IOException, ServletException {
        String json = "Incorrect json";
        try (StringReader stringReader = new StringReader(json);
             BufferedReader reader = new BufferedReader(stringReader);
             StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            when(request.getReader()).thenReturn(reader);
            when(response.getWriter()).thenReturn(writer);
            when(request.getSession().getId()).thenReturn("someid");
            signIn.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_INVALID_JSON));
        }
    }

    @Test
    public void testDoPostInvalidRequest() throws IOException, ServletException {
        String json = "{}";
        try (StringReader stringReader = new StringReader(json);
             BufferedReader reader = new BufferedReader(stringReader);
             StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            when(request.getReader()).thenReturn(reader);
            when(response.getWriter()).thenReturn(writer);
            when(request.getSession().getId()).thenReturn("someid");
            signIn.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_INVALID_REQUEST));
        }
    }

    @Test
    public void testDoPostUserNotExists() throws IOException, ServletException {
        JsonObject jo = new JsonObject();
        jo.addProperty(SignIn.EMAIL, "bag@rm.ru");
        jo.addProperty(SignIn.PASSWORD, "some");
        try (StringReader stringReader = new StringReader(jo.toString());
             BufferedReader reader = new BufferedReader(stringReader);
             StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            when(request.getReader()).thenReturn(reader);
            when(response.getWriter()).thenReturn(writer);
            when(request.getSession().getId()).thenReturn("someid");
            signIn.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_USER_NOT_EXISTS));
        }
    }

    @Test
    public void testDoPostOK() throws IOException, ServletException {
        JsonObject jo = new JsonObject();
        jo.addProperty(SignIn.EMAIL, "bag@rm.ru");
        jo.addProperty(SignIn.PASSWORD, "some");
        UserDataSet user = new UserDataSet("bag@rm.ru", "pass");
        ACCOUNTSERVICE.registerUser("bag@rm.ru", user);
        try (StringReader stringReader = new StringReader(jo.toString());
             BufferedReader reader = new BufferedReader(stringReader);
             StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            when(request.getReader()).thenReturn(reader);
            when(response.getWriter()).thenReturn(writer);
            when(request.getSession().getId()).thenReturn("someid");
            signIn.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(SignIn.ID));
        }
    }

}