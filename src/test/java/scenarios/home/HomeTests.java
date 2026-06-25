package scenarios.home;

import static org.junit.jupiter.api.Assertions.assertEquals;

import andycprojects.models.config.TestConfig;
import andycprojects.models.product.ProductOverview;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import andycprojects.ui.StoreHome;
import support.LifeCycle;
import support.tags.SetupPerClass;

import java.util.List;

@SetupPerClass
public class HomeTests extends LifeCycle {

    private StoreHome storeHome;
    private TestConfig testConfig;

    @Override
    public void onSetup() {
        // Arrange / Act
        storeHome = get(StoreHome.class);
        testConfig = get(TestConfig.class);
        storeHome.goTo();
    }

    @Tag("smoke")
    @Tag("home")
    @Test
    public void verifyTitle() {
        // Assert
        assertEquals("Emulsive Store", storeHome.getTitle());
    }

    @Tag("smoke")
    @Tag("home")
    @Test
    public void verifyFeaturedProducts() {
        // Arrange
        var imageServerUrl = testConfig.getStore().getImageServerUrl();
        List<ProductOverview> expectedFeaturedProducts = List.of(
                new ProductOverview(19, imageServerUrl + "assets/KodakGold200-35mmFilm-36exp.webp", "Gold 200", "£9.50", "/products/19"),
                new ProductOverview(6, imageServerUrl + "assets/Ilford_HP5_Plus_-_35mm_Film_-_Retro_Packaging.webp", "Hp5 Plus", "£7.35", "/products/6"),
                new ProductOverview(7, imageServerUrl + "assets/kodak-portra-400-35mm-film-877035.webp", "Portra 400", "£12.90", "/products/7"),
                new ProductOverview(24, imageServerUrl + "assets/cinestill-800t-120-film-471304.webp", "800T", "£19.00", "/products/24"),
                new ProductOverview(25, imageServerUrl + "assets/rollei-infrared-120-film-at-analogue-wonderland-922102.webp", "Rollei Infrared", "£19.00", "/products/25")
        );

        // Act
        var featuredProducts = storeHome.getFeaturedProducts();

        // Assert
        assertEquals(expectedFeaturedProducts, featuredProducts);
    }
}
