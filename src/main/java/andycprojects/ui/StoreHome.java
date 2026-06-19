package andycprojects.ui;

import andycprojects.models.config.TestConfig;
import andycprojects.models.product.ProductOverview;
import andycprojects.ui.common.INavigateTo;
import andycprojects.ui.common.ProductGrid;
import com.google.inject.Inject;
import com.microsoft.playwright.Page;

import java.util.List;

public class StoreHome implements INavigateTo {
    private final Page page;
    private final TestConfig testConfig;
    private final ProductGrid productGrid;

    @Inject
    public StoreHome(Page page, TestConfig testConfig, ProductGrid productGrid)
    {
        this.page = page;
        this.testConfig = testConfig;
        this.productGrid = productGrid;
    }

    public void goTo() {
        page.navigate(testConfig.getStore().getUrl());
    }

    public String getTitle() {
        return page.title();
    }

    public List<ProductOverview> getFeaturedProducts() {
        return productGrid.getProductOverviews();
    }
}
