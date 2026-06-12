package andycprojects.ui;

import andycprojects.models.config.TestConfig;
import com.google.inject.Inject;
import com.microsoft.playwright.Page;

public class StoreHome {
    private final Page page;
    private final TestConfig testConfig;

    @Inject
    public StoreHome(Page page, TestConfig testConfig )
    {
        this.page = page;
        this.testConfig = testConfig;
    }

    public void navigateTo() {
        page.navigate(testConfig.getStore().getUrl());
    }

    public String getTitle() {
        return page.title();
    }
}
