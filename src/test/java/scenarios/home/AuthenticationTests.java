package scenarios.home;

import andycprojects.models.testdata.UserType;
import andycprojects.models.ui.NavSection;
import andycprojects.ui.LoginPage;
import andycprojects.ui.StoreHome;
import andycprojects.ui.common.Header;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import support.LifeCycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("smoke")
@Tag("authentication")
public class AuthenticationTests extends LifeCycle
{
    private LoginPage loginPage;
    private Header header;

    @Override
    protected void onSetup()
    {
        StoreHome storeHome = injector.getInstance(StoreHome.class);
        loginPage = injector.getInstance(LoginPage.class);
        header = injector.getInstance(Header.class);
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
