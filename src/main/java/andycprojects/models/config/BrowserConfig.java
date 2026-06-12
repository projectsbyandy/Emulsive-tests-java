package andycprojects.models.config;

public class BrowserConfig {
    private String browserInTest;
    private Boolean isHeadless;

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
}