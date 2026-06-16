package andycprojects.ui;

import andycprojects.models.testdata.User;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import jakarta.inject.Inject;

public class LoginPage {
    private final Page page;

    @Inject
    public LoginPage(Page page) {
        this.page = page;
    }

    public void login(User user) {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("email")).fill(user.email());
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("password")).fill(user.password());
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
    }

    public void loginAsGuest() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Guest User")).click();
    }
}