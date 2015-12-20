package ru.bagrusss.apiservlets;

import com.google.gson.Gson;
import ru.bagrusss.helpers.Resourses;
import ru.bagrusss.main.Context;
import ru.bagrusss.main.Main;
import ru.bagrusss.servces.account.AccountService;

import javax.servlet.http.HttpServlet;
import java.util.logging.Logger;

/**
 * Created by vladislav
 */
@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {

    protected static final String PASSWORD1 = "password1";
    protected static final String PASSWORD2 = "password2";
    protected static final String PASSWORD = "password";
    protected static final String EMAIL = "email";
    protected static final String TYPE = "tp";
    protected static final String ID = "id";

    protected final Gson mGson = Resourses.GSON;
    protected Logger mLogger = Logger.getLogger(this.getClass().getCanonicalName());
    protected final Context mContext = Main.getAppContext();
    protected final AccountService mAccountService = (AccountService) mContext.get(AccountService.class);

    public static final String DEFAULT_ENCODING = "UTF-8";
}
