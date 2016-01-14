package ru.bagrusss.main;

import org.junit.Assert;
import org.junit.Test;
import ru.bagrusss.helpers.InitException;
import ru.bagrusss.servces.account.AccountService;
import ru.bagrusss.servces.account.AccountServiceFake;

/**
 * Created by vladislav
 */

@SuppressWarnings("deprecation")
public class ContextTest {

    private final AccountService accountService = new AccountServiceFake();

    @Test(expected = InitException.class)
    public void testContext() throws InitException {
        Context context = new Context();
        context.add(AccountService.class, accountService);
        Assert.assertNotEquals(context.get(AccountService.class), null);
        context.add(AccountService.class, accountService);
    }
}