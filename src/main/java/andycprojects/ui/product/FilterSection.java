package andycprojects.ui.product;

import andycprojects.models.product.FilterOption;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import jakarta.inject.Inject;

public class FilterSection {
    private final Page page;

    @Inject
    public FilterSection(Page page) {
        this.page = page;
    }

    public void set(FilterOption filterOption, String value) {
        filterOption.apply(page, value);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
    }
}