package order;

import andycprojects.models.testdata.UserType;
import andycprojects.ui.StoreHome;
import org.junit.jupiter.api.Test;
import support.LifeCycle;
import support.helpers.LoginHelper;

public class OrderTests extends LifeCycle {
    private StoreHome storeHome;
    private LoginHelper loginHelper;

    @Override
    protected void onSetup() {
        loginHelper = injector.getInstance(LoginHelper.class);
        storeHome = injector.getInstance(StoreHome.class);
        storeHome.navigateTo();
    }

    @Test
    public void OrderE2ETest() {
        loginHelper.as(testConfig.getUser(UserType.TEST_USER));
    }
}
