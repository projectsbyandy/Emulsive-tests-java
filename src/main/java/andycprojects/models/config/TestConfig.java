package andycprojects.models.config;

public class TestConfig {
    private StoreConfig store;
    private BrowserConfig browser;

    public TestConfig() {}

    public StoreConfig getStore() {
        return store;
    }

    public void setStore(StoreConfig store) {
        this.store = store;
    }

    public BrowserConfig getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserConfig browser) {
        this.browser = browser;
    }
}
