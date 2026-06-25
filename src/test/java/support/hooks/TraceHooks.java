package support.hooks;

import andycprojects.models.config.TestConfig;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class TraceHooks {

    private static final Logger log = LoggerFactory.getLogger(TraceHooks.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy-HHmm");

    private final BrowserContext browserContext;
    private final TestConfig testConfig;

    public TraceHooks(BrowserContext browserContext, TestConfig testConfig) {
        this.browserContext = browserContext;
        this.testConfig = testConfig;
    }

    public void startTrace() {
        if (testConfig.getBrowser().getTraceEnabled())
            browserContext.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
    }

    public void stopTrace(boolean testFailed, ExtensionContext context) {
        if (!testConfig.getBrowser().getTraceEnabled())
            return;

        if (!testFailed) {
            browserContext.tracing().stop();
            return;
        }

        var safeName = context.getDisplayName()
                .replaceAll("[()]", "")
                .replaceAll("[^a-zA-Z0-9]", "_");

        ZonedDateTime nowUtc = Instant.now().atZone(ZoneOffset.UTC);
        String formattedDateTime = nowUtc.format(formatter);

        Path tracePath = Paths.get("traces/" + safeName + "-" + formattedDateTime + "-FAILED.zip");
        Path absolutePath = tracePath.toAbsolutePath().normalize();

        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(absolutePath));

        log.error("Test failed, trace saved to {}", absolutePath);

        context.publishReportEntry(Map.of(
                "trace", absolutePath.toString(),
                "test", context.getDisplayName(),
                "browser", testConfig.getBrowser().getBrowserInTest()
        ));
    }
}
