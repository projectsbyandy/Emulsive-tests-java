package andycprojects.ui.common;

import andycprojects.models.ui.NavSection;
import com.google.inject.Inject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NavLinks {
    private final Page page;

    @Inject
    public NavLinks(Page page) {
        this.page = page;
    }

    public void select(NavSection section) {
        getLink(section).click();
    }

    public Locator getLink(NavSection section) {
        return this.page.getByTestId(String.format("nav-link-%s", section.name().toLowerCase()));
    }
}