package scenarios.order;

import andycprojects.models.testdata.UserType;
import andycprojects.ui.StoreHome;
import org.junit.jupiter.api.Test;
import support.LifeCycle;
import support.helpers.LoginHelper;
import support.helpers.ProductsHelper;

public class OrderTests extends LifeCycle {
    private StoreHome storeHome;
    private LoginHelper loginHelper;
    private ProductsHelper productsHelper;

    @Override
    protected void onSetup() {
        loginHelper = injector.getInstance(LoginHelper.class);
        storeHome = injector.getInstance(StoreHome.class);
        productsHelper = injector.getInstance(ProductsHelper.class);
        storeHome.navigateTo();
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
