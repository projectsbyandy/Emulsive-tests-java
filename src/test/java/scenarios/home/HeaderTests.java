package scenarios.home;

import andycprojects.models.ui.NavSection;
import andycprojects.ui.LoginPage;
import andycprojects.ui.StoreHome;
import andycprojects.ui.common.Header;
import com.microsoft.playwright.assertions.LocatorAssertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import support.LifeCycle;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HeaderTests extends LifeCycle {
    private Header header;
    private LoginPage loginPage;

    @Override
    protected void onSetup() {
        // Arrange
        StoreHome storeHome = injector.getInstance(StoreHome.class);
        header = injector.getInstance(Header.class);
        loginPage = injector.getInstance(LoginPage.class);

        // Act
        storeHome.navigateTo();
    }

    @Tag("smoke")
    @Test
    public void defaultHeadersDisplayedOnLoad() {
        // Assert
        assertThat(header.userManagement().getLink(NavSection.Login)).isVisible();
        assertThat(header.userManagement().getLink(NavSection.Register)).isVisible();
        assertThat(header.navLinks().getLink(NavSection.Home)).isVisible();
        assertThat(header.navLinks().getLink(NavSection.About)).isVisible();
        assertThat(header.navLinks().getLink(NavSection.Products)).isVisible();
        assertThat(header.navLinks().getLink(NavSection.Cart)).isVisible();
    }

    @Tag("smoke")
    @Test
    public void hiddenHeadersNotDisplayedOnLoad() {

        var visibleOptions = new LocatorAssertions.IsVisibleOptions().setTimeout(2000);

        // Assert
        assertThat(header.userManagement().getLink(NavSection.Logout))
                .not()
                .isVisible(visibleOptions);

        assertThat(header.navLinks().getLink(NavSection.Checkout))
                .not()
                .isVisible(visibleOptions);

        assertThat(header.navLinks().getLink(NavSection.Orders))
                .not()
                .isVisible(visibleOptions);
    }

    @Tag("smoke")
    @Test
    public void hiddenLinksDisplayedForLoggedInGuestUsers() {
        // Arrange
        header.userManagement().select(NavSection.Login);

        // Act
        loginPage.loginAsGuest();

        // Assert
        assertThat(header.userManagement().getLink(NavSection.Logout)).isVisible();
        assertThat(header.navLinks().getLink(NavSection.Checkout)).isVisible();
        assertThat(header.navLinks().getLink(NavSection.Orders)).isVisible();
    }
}
