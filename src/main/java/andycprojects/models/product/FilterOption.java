package andycprojects.models.product;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public enum FilterOption {
    Keyword {
        @Override
        public void apply(Page page, String value) {
            page.locator("#keyword").fill(value);
        }
    },
    Format {
        @Override
        public void apply(Page page, String value) {
            selectValueFromDropdown(page, "#format", value);
        }
    },
    Manufacturer {
        @Override
        public void apply(Page page, String value) {
            selectValueFromDropdown(page, "#manufacturer", value);
        }
    },
    OrderBy {
        @Override
        public void apply(Page page, String value) {
            selectValueFromDropdown(page, "#orderby", value);
        }
    },
    Price {
        @Override
        public void apply(Page page, String value) {
            page.locator("#price").fill(value);
        }
    },
    OnSale {
        @Override
        public void apply(Page page, String value) {
            var onsaleLocator = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("On Sale"));
            if (value.equals("checked") && !(onsaleLocator.isChecked())) {
                onsaleLocator.click();
            }
        }
    };

    public abstract void apply(Page page, String value);
    private static void selectValueFromDropdown(Page page, String selector, String value) {
        page.locator(selector).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(value)).click();
    }
}
