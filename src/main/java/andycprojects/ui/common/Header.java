package andycprojects.ui.common;

import jakarta.inject.Inject;

public record Header(NavLinks navLinks, UserManagement userManagement) {
    @Inject
    public Header {
    }
}