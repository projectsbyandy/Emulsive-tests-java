package home;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import andycprojects.ui.StoreHome;
import support.LifeCycle;

public class HomeTests extends LifeCycle {

    private StoreHome storeHome;

    @Override
    protected void onSetup() {
        // Arrange
        storeHome = injector.getInstance(StoreHome.class);

        // Act
        storeHome.navigateTo();
    }

    @Tag("smoke")
    @Tag("home")
    @Test
    public void verifyTitle() {
        // Assert
        assertEquals("Emulsive Store", storeHome.getTitle());
    }
}
