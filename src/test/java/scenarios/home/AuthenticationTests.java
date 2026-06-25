package scenarios.home;

import andycprojects.models.config.TestConfig;
import andycprojects.models.testdata.UserType;
import andycprojects.models.ui.NavSection;
import andycprojects.ui.LoginPage;
import andycprojects.ui.StoreHome;
import andycprojects.ui.common.Header;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import support.LifeCycle;
import support.tags.SetupPerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SetupPerTest
@Tag("smoke")
@Tag("authentication")
public class AuthenticationTests extends LifeCycle
{
    private StoreHome storeHome;
    private LoginPage loginPage;
    private Header header;
    private TestConfig testConfig;

    @Override
    public void onSetup()
    {
        storeHome = get(StoreHome.class);
        loginPage = get(LoginPage.class);
        header = get(Header.class);
        testConfig = get(TestConfig.class);

        storeHome.goTo();
    }

    @Test
    public void loginAsATestUser()
    {
        // Arrange
        header.userManagement().select(NavSection.Login);

        // Act
        loginPage.login(testConfig.getUser(UserType.TEST_USER));

        // Assert
        assertEquals("Hello BobDoe", header.userManagement().getGreetingText());
    }

    @Test
    public void loginAsAGuestUser()
    {
        // Arrange
        header.userManagement().select(NavSection.Login);

        // Act
        loginPage.loginAsGuest();

        // Assert
        assertEquals("Hello Dave Test", header.userManagement().getGreetingText());
    }
}
