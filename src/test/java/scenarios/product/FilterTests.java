package scenarios.product;

import andycprojects.models.product.FilterOption;
import andycprojects.ui.StoreHome;
import andycprojects.ui.product.ProductsPage;
import support.LifeCycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import support.tags.SetupPerClass;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SetupPerClass
public class FilterTests extends LifeCycle {

    private StoreHome storeHome;
    private ProductsPage productsPage;

    @Override
    public void onSetup() {
        storeHome = get(StoreHome.class);
        productsPage = get(ProductsPage.class);

        storeHome.goTo();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("filterCases")
    public void filteringShouldPersist(String label, FilterOption option, String input, String expected) {
        // Arrange
        productsPage.goTo();

        // Act
        productsPage.filterSection().reset();
        productsPage.filterSection().set(option, input);

        // Assert
        assertEquals(expected, productsPage.filterSection().get(option));
    }

    static Stream<Arguments> filterCases() {
        return Stream.of(
                Arguments.of("Keyword filter", FilterOption.Keyword, "portra", "portra"),
                Arguments.of("Format filter", FilterOption.Format, "35mm", "35mm"),
                Arguments.of("OnSale filter", FilterOption.OnSale, "checked", "on"),
                Arguments.of("OrderBy filter", FilterOption.OrderBy, "z-a", "z-a"),
                Arguments.of("Price filter", FilterOption.Price, "10", "10")
        );
    }
}