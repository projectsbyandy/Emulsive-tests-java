package andycprojects.ui.common;

import andycprojects.models.product.ProductOverview;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductGrid {
    private final Page page;

    @Inject
    public ProductGrid(Page page) {
        this.page = page;
    }

    public List<ProductOverview> getProductOverviews() {
        final String productOverviews = "[data-testid='products'] a[href^='/products']";
        var productLocator = page.locator(productOverviews).filter(new Locator.FilterOptions().setVisible(true));

        var productOverviewsList = new ArrayList<ProductOverview>();

        for (var product : productLocator.all()) {
            var detailsUrl = product.getAttribute("href");
            var imageUrl = product.locator("div img").getAttribute("src");
            var name = product.locator("h2").textContent();
            var priceWithCurrency = product.locator("div p").textContent();
            var id = selectIdFromDetailsUrl(detailsUrl);

            productOverviewsList.add(new ProductOverview(id, imageUrl, name, priceWithCurrency, detailsUrl));
        }
        return productOverviewsList;
    }

    private int selectIdFromDetailsUrl(String detailsUrl) {
        Pattern regex = Pattern.compile("products/(\\d+)");
        Matcher match = regex.matcher(detailsUrl);

        if (match.find()) {
            return Integer.parseInt(match.group(1));
        } else {
            throw new RuntimeException("Unable to extract id from Product url");
        }
    }
}
