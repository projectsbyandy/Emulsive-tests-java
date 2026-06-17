package support.hooks;

import andycprojects.models.config.TestConfig;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaywrightHooks {

    private static final Logger log = LoggerFactory.getLogger(PlaywrightHooks.class);

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    protected Page page;

    public void setupPlaywrightComponents(TestConfig testConfig) {
        playwright = Playwright.create();
        browser = createBrowser(playwright, testConfig);
        browserContext = browser.newContext();
        browserContext.setDefaultTimeout(testConfig.getBrowser().getElementTimeoutMs());
        page = browserContext.newPage();

        log.info("Playwright components setup: playwright: " + playwright.hashCode());
    }

    public void teardownPlaywrightComponents() {
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        log.info("Playwright components teardown complete");

    }

    private static Browser createBrowser(Playwright playwright, TestConfig testConfig) {
        return switch (testConfig.getBrowser().getBrowserInTest().toLowerCase()) {
            case "chromium", "chrome" -> playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(testConfig.getBrowser().getIsHeadless()));
            case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions()
                    .setHeadless(testConfig.getBrowser().getIsHeadless()));
            case "webkit", "safari" -> playwright.webkit().launch(new BrowserType.LaunchOptions()
                    .setHeadless(testConfig.getBrowser().getIsHeadless()));
            default -> throw new IllegalArgumentException(
                    "Unsupported browser type: " + testConfig.getBrowser().getBrowserInTest()
            );
        };
    }

    public Page getPage() {
        return page;
    }

    public BrowserContext getBrowserContext() {
        return browserContext;
    }
}
