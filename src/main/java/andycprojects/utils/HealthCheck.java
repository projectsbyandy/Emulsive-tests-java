package andycprojects.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import andycprojects.exceptions.TestAssertionException;
import andycprojects.models.config.TestConfig;

public class HealthCheck {
    private final HttpClient httpClient;
    private final TestConfig testConfig;

    public HealthCheck(HttpClient httpClient, TestConfig testConfig) {
        this.httpClient = httpClient;
        this.testConfig = testConfig;
    }

    public void verifyUiSiteResponding() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(testConfig.getStore().getUrl()))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new TestAssertionException("Health check on UI failed with status: " + response.statusCode());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TestAssertionException("Health check was interrupted due to", e);
        } catch (IOException e) {
            throw new TestAssertionException("Health check failed due to: ", e);
        }
    }
}
