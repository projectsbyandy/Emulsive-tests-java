package home;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import andycprojects.ui.StoreHome;
import support.LifeCycle;

public class HomeParallelTest extends LifeCycle {
    private StoreHome storeHome;

    @Override
    protected void onSetup() {
        storeHome = injector.getInstance(StoreHome.class);
    }

    @Test
    public void verifyTitle() {
        storeHome.navigateTo();
        assertEquals("Emulsive Store", storeHome.getTitle());
    }

    @Test
    public void verifyTitleForParallelTest() {
        storeHome.navigateTo();
        assertEquals("Emulsive Store", storeHome.getTitle());
    }
}
