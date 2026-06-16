package andycprojects.utils;

import andycprojects.models.config.TestConfig;
import andycprojects.models.testdata.User;
import andycprojects.models.testdata.UserType;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigLoader {
    private static Config cachedConfig;
    private static Map<UserType, User> cachedUsers;

    public static TestConfig load() {
        TestConfig config = ConfigBeanFactory.create(getConfig(), TestConfig.class);
        config.setResolvedUsers(cachedUsers);
        return config;
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

            cachedUsers = getUsers(cachedConfig);
        }

        return cachedConfig;
    }

    private static Map<UserType, User> getUsers(Config config) {
        Config usersConfig = config.getConfig("users");

        return Arrays.stream(UserType.values())
                .collect(Collectors.toMap(
                        userType -> userType,
                        userType -> {
                            Config userConfig = usersConfig.getConfig(userType.getConfigKey());
                            return new User(
                                    userConfig.getString("email"),
                                    userConfig.getString("password")
                            );
                        }
                ));
    }
}
