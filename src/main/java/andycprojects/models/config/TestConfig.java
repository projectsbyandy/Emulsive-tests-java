package andycprojects.models.config;

import andycprojects.models.testdata.User;
import andycprojects.models.testdata.UserType;

import java.util.Map;

public class TestConfig {
    private StoreConfig store;
    private BrowserConfig browser;
    private Map<UserType, User> users;

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

    public User getUser(UserType type) {
        return users.get(type);
    }

    public void setResolvedUsers(Map<UserType, User> users) {
        this.users = users;
    }
}