package andycprojects.models.testdata;

public enum UserType {
    TEST_USER("testUser");

    private final String configKey;

    UserType(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigKey() {
        return configKey;
    }
}
