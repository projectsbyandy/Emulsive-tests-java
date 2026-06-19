package andycprojects.ui.product;

import andycprojects.models.product.FilterOption;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import jakarta.inject.Inject;

public class FilterSection {
    private final Page page;

    @Inject
    public FilterSection(Page page) {
        this.page = page;
    }

    public void set(FilterOption filterOption, String value) {
        filterOption.setValue(page, value);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
    }

    public String get(FilterOption filterOption) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForCondition(() -> page.url().contains(filterOption.name().toLowerCase()));
        return filterOption.getValue(page);
    }
}