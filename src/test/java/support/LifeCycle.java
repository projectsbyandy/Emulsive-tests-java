package support;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.TestWatcher;

import com.google.inject.Injector;

import support.hooks.*;

@ExtendWith(SuiteLevelHooks.class)
@ExtendWith(ClassLevelHooks.class)
@ExtendWith(TestLevelHooks.class)
@ExtendWith(TestFailureTrackingHooks.class)
public abstract class LifeCycle implements TestWatcher {

    private Injector injector;

    public void initResources(Injector injector) {
        this.injector = injector;
    }

    protected <T> T get(Class<T> type) {
        return injector.getInstance(type);
    }

    public void onSetup() {}
    public void onTeardown() {}
}
