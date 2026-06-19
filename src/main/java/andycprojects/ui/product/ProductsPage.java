package andycprojects.ui.product;

import andycprojects.models.ui.NavSection;
import andycprojects.ui.common.INavigateTo;
import andycprojects.ui.common.NavLinks;
import jakarta.inject.Inject;

public record ProductsPage(FilterSection filterSection, NavLinks navLinks) implements INavigateTo {
    @Inject
    public ProductsPage {
    }

    @Override
    public void goTo() {
        navLinks.select(NavSection.Products);
    }
}
