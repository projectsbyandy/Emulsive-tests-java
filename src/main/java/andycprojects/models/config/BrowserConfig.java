package andycprojects.models.config;

public class BrowserConfig {
    private String browserInTest;
    private Boolean isHeadless;
    private Boolean traceEnabled;

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
}