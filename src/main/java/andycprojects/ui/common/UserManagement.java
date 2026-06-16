package andycprojects.ui.common;

import andycprojects.models.ui.NavSection;
import com.google.inject.Inject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UserManagement {
    private final Page page;

    @Inject
    public UserManagement(Page page) {
        this.page = page;
    }

    public void select(NavSection section) {
        switch(section) {
            case NavSection.Login:
                page.getByTestId("login").click();
                break;
            case NavSection.Register:
                page.getByTestId("register").click();
                break;
            case NavSection.Logout:
                page.getByTestId("logout").click();
                break;
        }
    }

    public Locator getLink(NavSection section) {
        return this.page.getByTestId(section.name().toLowerCase());
    }

    public String getGreetingText() {
        return page.getByTestId("greeting").innerText();
    }
}