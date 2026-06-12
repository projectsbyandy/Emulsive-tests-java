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
        storeHome = injector.getInstance(StoreHome.class);
    }

    @Tag("smoke")
    @Tag("home")
    @Test
    public void verifyTitle() {
        storeHome.navigateTo();
        assertEquals("Emulsive Store", storeHome.getTitle());
    }

    @Tag("smoke")
    @Test
    public void verifyTitleForParallelTest() {
        storeHome.navigateTo();
        assertEquals("Emulsive Store", storeHome.getTitle());
    }
}
