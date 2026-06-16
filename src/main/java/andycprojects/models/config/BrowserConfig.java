package andycprojects.models.config;

public class BrowserConfig {
    private String browserInTest;
    private Boolean isHeadless;
    private Boolean traceEnabled;
    private int elementTimeoutMs;

    public String getBrowserInTest() {
        return browserInTest;
    }

    public void setBrowserInTest(String browser) {
        this.browserInTest = browser;
    }

    public Boolean getIsHeadless() {
        return isHeadless;
    }

    public void setIsHeadless(Boolean isHeadless) {
        this.isHeadless = isHeadless;
    }

    public Boolean getTraceEnabled() {
        return traceEnabled;
    }

    public void setTraceEnabled(Boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
    }

    public int getElementTimeoutMs() {
        return elementTimeoutMs;
    }

    public void setElementTimeoutMs(int elementTimeoutMs) {
        this.elementTimeoutMs = elementTimeoutMs;
    }
}