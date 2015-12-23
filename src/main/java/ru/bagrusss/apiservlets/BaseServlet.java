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


@SuppressWarnings("ConstantNamingConvention")
public class BaseServlet extends HttpServlet {

    protected static final String PASSWORD1 = "password1";
    protected static final String PASSWORD2 = "password2";
    protected static final String PASSWORD = "password";
    protected static final String EMAIL = "email";
    protected static final String TYPE = "tp";
    protected static final String ID = "id";

    public static final String JSON_TYPE = "application/json; charset=utf-8";

    protected final Gson mGson = Resourses.GSON;
    protected final Logger log = Logger.getLogger(getClass().getCanonicalName());
    protected final Context mContext = Main.getAppContext();
    protected final AccountService mAccountService = (AccountService) mContext.get(AccountService.class);

    public static final String DEFAULT_ENCODING = "UTF-8";

}
