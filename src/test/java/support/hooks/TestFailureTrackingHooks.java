package support.hooks;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class TestFailureTrackingHooks implements TestExecutionExceptionHandler {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(TestFailureTrackingHooks.class);

    private static final String TEST_FAILED_KEY = "testFailed";

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        context.getStore(NAMESPACE).put(TEST_FAILED_KEY, true);
        throw throwable;
    }

    public static boolean hasFailed(ExtensionContext context) {
        return Boolean.TRUE.equals(
                context.getStore(NAMESPACE).get(TEST_FAILED_KEY, Boolean.class));
    }
}
