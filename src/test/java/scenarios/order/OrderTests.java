package scenarios.order;

import andycprojects.models.config.TestConfig;
import andycprojects.models.testdata.UserType;
import andycprojects.ui.StoreHome;
import org.junit.jupiter.api.Test;
import support.LifeCycle;
import support.helpers.LoginHelper;
import support.helpers.ProductsHelper;
import support.tags.SetupPerClass;

@SetupPerClass
public class OrderTests extends LifeCycle {
    private LoginHelper loginHelper;
    private ProductsHelper productsHelper;
    private TestConfig testConfig;

    @Override
    public void onSetup() {
        StoreHome storeHome = get(StoreHome.class);
        loginHelper = get(LoginHelper.class);
        productsHelper = get(ProductsHelper.class);
        testConfig = get(TestConfig.class);

        storeHome.goTo();
    }

    @Test
    public void OrderE2ETest() {
        // Arrange
        loginHelper.as(testConfig.getUser(UserType.TEST_USER));
        productsHelper.addProductsToCart("Portra 400", 2);

        // Act

        // Assert
    }
}
