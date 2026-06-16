package andycprojects.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;

public class ConfigLoader {
    private static Config cachedConfig;

    public static <T> T load(Class<T> configClass) {
        return ConfigBeanFactory.create(getConfig(), configClass);
    }
    public static <T> T load(Class<T> configClass, String rootPath) {
        return ConfigBeanFactory.create(getConfig().getConfig(rootPath), configClass);
    }

    public static <T> T loadAsRecord(String rootPath, java.util.function.Function<Config, T> mapper) {
        return mapper.apply(getConfig().getConfig(rootPath));
    }

    private static synchronized Config getConfig() {
        if (cachedConfig == null) {
            String env = System.getProperty("env",
                    System.getenv().getOrDefault("ENV", "local")).toLowerCase();

            cachedConfig = ConfigFactory
                    .parseResources("common-config.conf")
                    .withFallback(ConfigFactory.parseResources(env+"-config.conf"))
                    .withFallback(ConfigFactory.load())
                    .resolve();
        }
        return cachedConfig;
    }
}
