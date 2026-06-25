package support.hooks;

import andycprojects.models.config.TestConfig;
import andycprojects.utils.ConfigLoader;
import com.google.inject.Injector;
import com.microsoft.playwright.BrowserContext;
import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.LifeCycle;

public class TestLevelHooks implements BeforeEachCallback, AfterEachCallback {
    private static final Logger log = LoggerFactory.getLogger(TestLevelHooks.class);
    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(TestLevelHooks.class);

    public static final String INJECTOR_KEY = "injector";

    @Override
    public void beforeEach(ExtensionContext context) {
        if (!context.getTags().contains("setupPerClass")) {
            TestConfig testConfig = ConfigLoader.load();
            Injector injector = PlaywrightLifeCycle.createInjector(testConfig);

            log.info("Test level injector setup: {} for {}", injector.hashCode(), context.getDisplayName());

            new TraceHooks(injector.getInstance(BrowserContext.class),
                    injector.getInstance(TestConfig.class))
                    .startTrace();

            context.getStore(NAMESPACE).put(INJECTOR_KEY, injector);

            Object testInstance = context.getRequiredTestInstance();
            injector.injectMembers(testInstance);
            if (testInstance instanceof LifeCycle lifeCycle) {
                lifeCycle.initResources(injector);
                lifeCycle.onSetup();
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (!context.getTags().contains("setupPerClass")) {
            Injector injector = context.getStore(NAMESPACE)
                    .get(INJECTOR_KEY, Injector.class);

            if (injector == null) {
                log.warn("Injector not found in store for {} — setup may have failed",
                        context.getDisplayName());
                return;
            }

            var testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof LifeCycle lifeCycle) {
                lifeCycle.onTeardown();
            }

            new TraceHooks(injector.getInstance(BrowserContext.class),
                    injector.getInstance(TestConfig.class))
                    .stopTrace(TestFailureTrackingHooks.hasFailed(context), context);

            PlaywrightLifeCycle.close(injector);
        }
    }
}
