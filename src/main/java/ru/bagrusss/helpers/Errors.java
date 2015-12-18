package ru.bagrusss.helpers;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import ru.bagrusss.apiservlets.BaseServlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vladislav
 */
@SuppressWarnings("ALL")
public class Errors {
    public static final byte CODE_OK = 0; // - OK.
    public static final byte CODE_CANT_START_GAME = 1; //- невозможно запустить игру.
    public static final byte CODE_LOW_RULLES = 3;//    - не достаточно прав.
    public static final byte CODE_USER_NOT_EXISTS = 4;
    public static final byte CODE_USER_ALREADY_EXISTS = 5;// - пользователь уже существует.
    public static final byte CODE_INVALID_REQUEST = 6;// - некорректные параметры.
    public static final byte CODE_INVALID_JSON = 7; //- невалидный JSON.
    public static final byte CODE_PASSWORDS_ERROR = 8; //- пароли не совпадают.
    public static final byte CODE_ROOM_PLACE = 11;// - место в комнате закончилось.
    public static final byte CODE_ROOM_NOT_EXISTS = 12; //- комнаты не существует.
    public static final byte CODE_ROOM_NOT_CREATED = 13; //- невозможно создать комнату.
    public static final byte CODE_ROOM_MORE_ONE = 14; //- невозможно находиться более чем в одной комнате

    public static final String MESSAGE_CANT_START_GAME = "Невозможно запустить игру";
    public static final String MESSAGE_LOW_RULLES = "Не достаточно прав";
    public static final String MESSAGE_USER_NOT_EXISTS = "Пользователь не найден";
    public static final String MESSAGE_USER_ALREADY_EXISTS = "Пользователь уже существует";
    public static final String MESSAGE_INVALID_REQUEST = "Некорректный запрос";
    public static final String MESSAGE_INVALID_JSON = "Невалидный JSON";
    public static final String MESSAGE_PASSWORDS_ERROR = "Пароли не совпадают";
    public static final String MESSAGE_ROOM_PLACE = "Место в комнате закончилось";
    public static final String MESSAGE_ROOM_NOT_EXISTS = "Комнаты не существует";
    public static final String MESSAGE_ROOM_NOT_CREATED = "Невозможно создать комнату";
    public static final String MESSAGE_ROOM_MORE_ONE = "Невозможно находиться более чем в одной комнате";

    private static final String RESPONSE = "response";

    public static void correct(HttpServletResponse rsp, JsonObject resp) throws IOException {
        rsp.setStatus(HttpServletResponse.SC_OK);
        JsonObject response = new JsonObject();
        response.addProperty("code", CODE_OK);
        response.add(RESPONSE, resp);
        rsp.getWriter().write(response.toString());
    }

    public static void error404(HttpServletResponse rsp, @NotNull String msg) throws IOException {
        rsp.setCharacterEncoding(BaseServlet.DEFAULT_ENCODING);
        rsp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        rsp.getWriter().write(msg);
    }

    public static void errorInvalidJson(HttpServletResponse rsp) throws IOException {
        errorAPI(rsp, CODE_INVALID_JSON, MESSAGE_INVALID_JSON);
    }

    public static void errorPasswordsNotMatch(HttpServletResponse rsp) throws IOException {
        errorAPI(rsp, CODE_PASSWORDS_ERROR, MESSAGE_PASSWORDS_ERROR);
    }

    public static void errorAPI(HttpServletResponse rsp, byte code, String msg) throws IOException {
        rsp.setCharacterEncoding(BaseServlet.DEFAULT_ENCODING);
        rsp.setStatus(HttpServletResponse.SC_OK);
        JsonObject response = new JsonObject();
        response.addProperty("code", code);
        response.addProperty(RESPONSE, msg);
        rsp.getWriter().write(response.toString());
    }
}
