package andycprojects.ioc;

import java.net.http.HttpClient;
import java.time.Duration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import andycprojects.models.config.TestConfig;
import andycprojects.utils.HealthCheck;
import jakarta.inject.Singleton;

public class HealthCheckManager extends AbstractModule {
    @Override
    protected void configure() {
        install(new ConfigManager());
    }

    @Provides
    @Singleton
    HttpClient getHttpClient() {
        return HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Provides
    @Singleton
    HealthCheck getHealthCheck(HttpClient httpClient, TestConfig testConfig) {
        return new HealthCheck(httpClient, testConfig);
    };
}
