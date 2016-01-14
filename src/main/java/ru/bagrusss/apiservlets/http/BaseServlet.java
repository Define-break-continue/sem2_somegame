package ru.bagrusss.apiservlets.http;

import com.google.gson.Gson;
import ru.bagrusss.helpers.Resources;
import ru.bagrusss.main.Context;
import ru.bagrusss.servces.account.AccountService;

import javax.servlet.http.HttpServlet;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vladislav
 */


@SuppressWarnings("ConstantNamingConvention")
public abstract class BaseServlet extends HttpServlet {

    public static final String PASSWORD1 = "password1";
    public static final String PASSWORD2 = "password2";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ID = "id";
    public static final String COUNT = "count";

    public static final String JSON_TYPE = "application/json; charset=utf-8";
    public static final String DEFAULT_ENCODING = "UTF-8";
    protected Gson mGson = Resources.GSON;
    protected final Logger log = Logger.getLogger(getClass().getCanonicalName());
    protected final Context mContext;
    protected final AccountService mAccountService;

    public BaseServlet(Context context) {
        mContext = context;
        mAccountService = (AccountService) mContext.get(AccountService.class);
    }

    protected byte getMethod(String url, Pattern pattern) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? Byte.valueOf(matcher.group(1)) : 0;
    }

}
