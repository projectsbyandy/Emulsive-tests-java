package support.di;

import andycprojects.models.config.TestConfig;
import com.google.inject.AbstractModule;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaywrightModule extends AbstractModule {
    private static final Logger log = LoggerFactory.getLogger(PlaywrightModule.class);
    private final TestConfig testConfig;

    public PlaywrightModule(TestConfig testConfig) {
        this.testConfig = testConfig;
    }

    @Override
    protected void configure() {
        var playwright = Playwright.create();
        var browser = createBrowser(playwright, testConfig);

        var browserContext = browser.newContext();
        browserContext.setDefaultTimeout(testConfig.getBrowser().getElementTimeoutMs());

        var page = browserContext.newPage();

        log.info("Playwright components setup: playwright: {}", playwright.hashCode());

        bind(TestConfig.class).toInstance(testConfig);
        bind(Playwright.class).toInstance(playwright);
        bind(Browser.class).toInstance(browser);
        bind(BrowserContext.class).toInstance(browserContext);
        bind(Page.class).toInstance(page);
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
}
