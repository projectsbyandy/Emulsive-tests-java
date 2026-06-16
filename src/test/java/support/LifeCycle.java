package support;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.TestWatcher;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.microsoft.playwright.Page;

import andycprojects.ioc.HealthCheckManager;
import andycprojects.models.config.TestConfig;
import andycprojects.utils.ConfigLoader;
import andycprojects.utils.HealthCheck;
import support.hooks.PlaywrightHooks;
import support.hooks.TraceHooks;

@ExtendWith(TestFailureTrackingExtension.class)
public abstract class LifeCycle implements TestWatcher {
    // Per test
    private TestConfig testConfig;
    protected Injector injector;
    protected boolean testFailed;
    private PlaywrightHooks playwrightHooks;
    private TraceHooks traceHooks;

    @BeforeAll
    static void suiteSetup() {
        performHealthCheck();
    }

    @BeforeEach
    void setupDependencies() {
        testConfig = ConfigLoader.load(TestConfig.class);

        playwrightHooks = new PlaywrightHooks();
        playwrightHooks.setupPlaywrightComponents(testConfig);

        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Page.class).toInstance(playwrightHooks.getPage());
                bind(TestConfig.class).toInstance(testConfig);
            }
        });

        traceHooks = new TraceHooks(playwrightHooks.getBrowserContext(), testConfig);
        traceHooks.startTrace();

        onSetup();
    }

    @AfterEach
    void teardownPlaywrightComponents(TestInfo testInfo, TestReporter testReporter) {
        onTeardown();

        traceHooks.stopTrace(testFailed, testInfo, testReporter);
        playwrightHooks.teardownPlaywrightComponents();
    }

    protected void onSetup() {}
    protected void onTeardown() {}

    @SuppressWarnings("null")
    private static void performHealthCheck() {
        var hcInjector = Guice.createInjector(new HealthCheckManager());
        hcInjector.getInstance(HealthCheck.class).verifyUiSiteResponding();
    }
}
