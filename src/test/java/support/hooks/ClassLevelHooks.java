package support.hooks;

import andycprojects.models.config.TestConfig;
import andycprojects.utils.ConfigLoader;
import com.google.inject.Injector;
import com.microsoft.playwright.BrowserContext;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.LifeCycle;

public class ClassLevelHooks implements BeforeAllCallback, AfterAllCallback {

    private final Logger log = LoggerFactory.getLogger(ClassLevelHooks.class);

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(ClassLevelHooks.class);

    public static final String INJECTOR_KEY = "injector";

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (context.getTags().contains("setupPerClass")) {
            TestConfig testConfig = ConfigLoader.load();
            Injector injector = PlaywrightLifeCycle.createInjector(testConfig);

            log.info("Class level injector setup: {} for {}", injector.hashCode(), context.getRequiredTestClass().getName());

            new TraceHooks(injector.getInstance(BrowserContext.class),
                    injector.getInstance(TestConfig.class))
                    .startTrace();

            context.getStore(NAMESPACE).put(INJECTOR_KEY, injector);

            context.getTestInstances().ifPresent(instances ->
                    instances.getAllInstances().forEach(instance -> {
                        if (instance instanceof LifeCycle lifeCycle) {
                            lifeCycle.initResources(injector);
                            lifeCycle.onSetup();
                        }
                    })
            );
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {

        if (context.getTags().contains("setupPerClass")) {
            Injector injector = context.getStore(NAMESPACE)
                    .get(INJECTOR_KEY, Injector.class);

            if (injector == null) {
                log.warn("Injector not found in store for {} — setup may have failed",
                        context.getDisplayName());

                return;
            }

            context.getTestInstances().ifPresent(instances ->
                    instances.getAllInstances().forEach(instance -> {
                        if (instance instanceof LifeCycle lifeCycle) {
                            lifeCycle.onTeardown();
                        }
                    })
            );

            new TraceHooks(injector.getInstance(BrowserContext.class),
                    injector.getInstance(TestConfig.class))
                    .stopTrace(TestFailureTrackingHooks.hasFailed(context), context);

            PlaywrightLifeCycle.close(injector);
        }
    }
}
