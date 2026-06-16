package support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class TestFailureTrackingExtension implements TestExecutionExceptionHandler {

    public void handleTestExecutionException(ExtensionContext context, Throwable throwable)
            throws Throwable {

        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof LifeCycle) {
            ((LifeCycle) testInstance).testFailed = true;
        }

        throw throwable;
    }
}