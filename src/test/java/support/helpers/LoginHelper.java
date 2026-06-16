package support.helpers;

import andycprojects.models.testdata.User;
import andycprojects.models.ui.NavSection;
import andycprojects.ui.LoginPage;
import andycprojects.ui.common.Header;
import jakarta.inject.Inject;

public class LoginHelper {
    private Header header;
    private LoginPage loginPage;

    @Inject
    public LoginHelper(Header header, LoginPage loginPage) {
        this.header = header;
        this.loginPage = loginPage;
    }

    public void as(User user) {
        header.userManagement().select(NavSection.Login);
        loginPage.login(user);
    }
}
