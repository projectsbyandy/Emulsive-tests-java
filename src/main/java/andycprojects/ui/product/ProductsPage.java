package andycprojects.ui.product;

import jakarta.inject.Inject;

public record ProductsPage(FilterSection filterSection) {
    @Inject
    public ProductsPage {
    }
}
