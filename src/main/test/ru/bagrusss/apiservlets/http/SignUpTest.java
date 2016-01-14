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

public class SignUpTest extends BaseServletTest {

    final SignUp signUp = new SignUp(CONTEXT);

    @Test
    public void testDoPostUserAuthorized() throws IOException, ServletException {
        try (StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            ACCOUNTSERVICE.addSession("someid", new UserDataSet("email@rm.rf", "pass"));
            when(response.getWriter()).thenReturn(writer);
            when(request.getSession().getId()).thenReturn("someid");
            signUp.doPost(request, response);
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
            when(response.getWriter()).thenReturn(writer);
            when(request.getReader()).thenReturn(reader);
            when(request.getSession().getId()).thenReturn("someid");
            signUp.doPost(request, response);
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
            when(request.getSession().getId()).thenReturn("someid");
            when(response.getWriter()).thenReturn(writer);
            signUp.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_INVALID_REQUEST));
        }
    }

    @Test
    public void testDoPostPasswordsError() throws IOException, ServletException {
        JsonObject jo = new JsonObject();
        jo.addProperty(SignIn.EMAIL, "bag@rm.ru");
        jo.addProperty(SignIn.PASSWORD1, "some");
        jo.addProperty(SignIn.PASSWORD2, "some1");
        try (StringReader stringReader = new StringReader(jo.toString());
             BufferedReader reader = new BufferedReader(stringReader);
             StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            when(request.getReader()).thenReturn(reader);
            when(response.getWriter()).thenReturn(writer);
            when(request.getSession().getId()).thenReturn("someid");
            signUp.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_PASSWORDS_ERROR));
        }
    }

    @Test
    public void testDoPostUserExists() throws IOException, ServletException {
        JsonObject jo = new JsonObject();
        jo.addProperty(SignIn.EMAIL, "bag@rm.ru");
        jo.addProperty(SignIn.PASSWORD1, "some");
        jo.addProperty(SignIn.PASSWORD2, "some");
        ACCOUNTSERVICE.registerUser("bag@rm.ru", new UserDataSet(1, "bag@rm.ru", "some"));
        try (StringReader stringReader = new StringReader(jo.toString());
             BufferedReader reader = new BufferedReader(stringReader);
             StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            when(request.getReader()).thenReturn(reader);
            when(response.getWriter()).thenReturn(writer);
            when(request.getSession().getId()).thenReturn("someid");
            signUp.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(Errors.MESSAGE_USER_ALREADY_EXISTS));
            Assert.assertEquals(ACCOUNTSERVICE.getCountRegisteredUsers(), 1);
        }
    }

    @Test
    public void testDoPostUserOK() throws IOException, ServletException {
        JsonObject jo = new JsonObject();
        jo.addProperty(SignIn.EMAIL, "bag@rm.ru");
        jo.addProperty(SignIn.PASSWORD1, "some");
        jo.addProperty(SignIn.PASSWORD2, "some");
        try (StringReader stringReader = new StringReader(jo.toString());
             BufferedReader reader = new BufferedReader(stringReader);
             StringWriter stringWriter = new StringWriter();
             PrintWriter writer = new PrintWriter(stringWriter)) {
            when(request.getReader()).thenReturn(reader);
            when(request.getSession().getId()).thenReturn("someid");
            when(response.getWriter()).thenReturn(writer);
            signUp.doPost(request, response);
            Assert.assertTrue(stringWriter.toString().contains(SignUp.ID));
            Assert.assertEquals(ACCOUNTSERVICE.getCountRegisteredUsers(), 1);
        }
    }
}