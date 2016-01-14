package base;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;
import ru.bagrusss.helpers.InitException;
import ru.bagrusss.main.Context;
import ru.bagrusss.servces.account.AccountService;
import ru.bagrusss.servces.account.AccountServiceFake;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

/**
 * Created by vladislav
 */
public class BaseServletTest {

    protected static final AccountService ACCOUNTSERVICE = new AccountServiceFake();
    protected static final Context CONTEXT = new Context();
    protected final HttpServletRequest request = Mockito.mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
    protected final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @BeforeClass
    public static void beforeClass() throws Exception {
        try {
            CONTEXT.add(AccountService.class, ACCOUNTSERVICE);
        } catch (InitException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void afterClass() throws Exception {
        CONTEXT.clear();
    }

    @After
    public void after() throws Exception {
        ACCOUNTSERVICE.removeAll();
    }

}
