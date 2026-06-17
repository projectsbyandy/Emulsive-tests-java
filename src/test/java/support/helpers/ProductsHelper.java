package support.helpers;

import andycprojects.models.product.FilterOption;
import andycprojects.models.ui.NavSection;
import andycprojects.ui.common.Header;
import andycprojects.ui.product.ProductsPage;
import jakarta.inject.Inject;

public class ProductsHelper {
    private final Header header;
    private final ProductsPage productsPage;

    @Inject
    public ProductsHelper(Header header, ProductsPage productsPage) {
        this.header = header;
        this.productsPage = productsPage;
    }

    public void addProductsToCart(String productName, int quantity) {
        header.navLinks().select(NavSection.Products);
        productsPage.filterSection().set(FilterOption.Keyword, productName);
    }
}
