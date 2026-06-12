package support;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import andycprojects.ioc.HealthCheckManager;
import andycprojects.models.config.TestConfig;
import andycprojects.utils.ConfigLoader;
import andycprojects.utils.HealthCheck;

public abstract class LifeCycle {

    // Per test class
    private static TestConfig testConfig;

    // Per test
    private Playwright playwright;
    private Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected Injector injector;

    @BeforeAll
    static void suiteSetup() {
        performHealthCheck();
        testConfig = ConfigLoader.load(TestConfig.class);
    }

    @BeforeEach
    void setupPlaywrightComponents() {
        playwright = Playwright.create();
        browser = createBrowser(playwright, testConfig);
        browserContext = browser.newContext();
        page = browserContext.newPage();

        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Page.class).toInstance(page);
                bind(TestConfig.class).toInstance(testConfig);
            }
        });

        onSetup();
    }

    @AfterEach
    void teardownPlaywrightComponents() {
        onTeardown();
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @AfterAll
    static void suiteTeardown() {
        ConfigLoader.resetCache();
    }

    protected void onSetup() {}
    protected void onTeardown() {}

    @SuppressWarnings("null")
    private static void performHealthCheck() {
        var hcInjector = Guice.createInjector(new HealthCheckManager());
        hcInjector.getInstance(HealthCheck.class).verifyUiSiteResponding();
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
