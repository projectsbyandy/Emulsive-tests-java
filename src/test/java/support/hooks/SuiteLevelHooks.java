package support.hooks;

import andycprojects.ioc.HealthCheckManager;
import andycprojects.utils.HealthCheck;
import com.google.inject.Guice;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuiteLevelHooks implements BeforeAllCallback {

    private static final Logger log = LoggerFactory.getLogger(SuiteLevelHooks.class);

    private static boolean healthChecked = false;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (healthChecked) {
            return;
        }

        synchronized (SuiteLevelHooks.class) {
            if (!healthChecked) {
                String pid = String.valueOf(ProcessHandle.current().pid());
                log.info("Suite setup — runs for fork with pid {}", pid);
                performHealthCheck();
                healthChecked = true;
            }
        }
    }

    private static void performHealthCheck() {
        var hcInjector = Guice.createInjector(new HealthCheckManager());
        hcInjector.getInstance(HealthCheck.class).verifyUiSiteResponding();
    }
}
