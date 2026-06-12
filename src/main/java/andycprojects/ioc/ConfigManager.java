package andycprojects.ioc;

import andycprojects.models.config.TestConfig;
import andycprojects.utils.ConfigLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import jakarta.inject.Singleton;

public class ConfigManager extends AbstractModule {
    @Provides
    @Singleton
    TestConfig getTestConfig() {
        return ConfigLoader.load(TestConfig.class);
    }
}
