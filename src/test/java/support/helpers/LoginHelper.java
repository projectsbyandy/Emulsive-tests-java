package support.helpers;

import andycprojects.models.config.TestConfig;
import andycprojects.models.testdata.User;
import andycprojects.models.ui.NavSection;
import andycprojects.ui.common.Header;
import jakarta.inject.Inject;

public class LoginHelper {
    private Header header;
    private TestConfig testConfig;

    @Inject
    public LoginHelper(Header header, TestConfig testConfig) {
        this.header = header;
        this.testConfig = testConfig;
    }

    public void as(User user) {
        header.userManagement().select(NavSection.Login);
    }
}
