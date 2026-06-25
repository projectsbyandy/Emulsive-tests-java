package support.hooks;

import andycprojects.models.config.TestConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.di.PlaywrightModule;

public class PlaywrightLifeCycle {
    private static final Logger log = LoggerFactory.getLogger(PlaywrightLifeCycle.class);

    private PlaywrightLifeCycle() {}

    public static Injector createInjector(TestConfig testConfig) {
        return Guice.createInjector(new PlaywrightModule(testConfig));
    }

    public static void close(Injector injector) {
        closeResource(injector, BrowserContext.class);
        closeResource(injector, Browser.class);
        closeResource(injector, Playwright.class);

        log.info("Playwright components teardown complete");
    }

    private static <T extends AutoCloseable> void closeResource(Injector injector, Class<T> resource) {
        try {
            injector.getInstance(resource).close();
        } catch(Exception e){
            log.info("Unable to find resource: {}", resource);
        }
    }
}
