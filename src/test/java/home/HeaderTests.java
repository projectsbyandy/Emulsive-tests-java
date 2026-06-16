package home;

import andycprojects.models.ui.NavSection;
import andycprojects.ui.StoreHome;
import andycprojects.ui.common.Header;
import com.microsoft.playwright.assertions.LocatorAssertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import support.LifeCycle;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HeaderTests extends LifeCycle {
    private Header header;

    @Override
    protected void onSetup() {
        StoreHome storeHome = injector.getInstance(StoreHome.class);
        header = injector.getInstance(Header.class);
        storeHome.navigateTo();
    }

    @Tag("smoke")
    @Test
    public void defaultHeadersDisplayedOnLoad() {
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
        assertThat(header.navLinks().getLink(NavSection.Logout))
                .not()
                .isVisible(visibleOptions);

        assertThat(header.userManagement().getLink(NavSection.Checkout))
                .not()
                .isVisible(visibleOptions);
    }
}
