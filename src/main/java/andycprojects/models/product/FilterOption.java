package andycprojects.models.product;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.function.Function;

public enum FilterOption {
    Keyword(page -> page.locator("#keyword")) {
        @Override
        public void setValue(Page page, String value) {
            locator.apply(page).fill(value);
        }

        @Override
        public String getValue(Page page) {
            return locator.apply(page).getAttribute("value");
        }
    },
    Format(page -> page.locator("#format")) {
        @Override
        public void setValue(Page page, String value) {
            selectValueFromDropdown(locator.apply(page), page, value);
        }

        @Override
        public String getValue(Page page) {
            return locator.apply(page).textContent();
        }
    },
    Manufacturer(page -> page.locator("#manufacturer")) {
        @Override
        public void setValue(Page page, String value) {
            selectValueFromDropdown(locator.apply(page), page, value);
        }

        @Override
        public String getValue(Page page) {
            return locator.apply(page).textContent();
        }
    },
    OrderBy(page -> page.locator("#orderby")) {
        @Override
        public void setValue(Page page, String value) {
            selectValueFromDropdown(locator.apply(page), page, value);
        }

        @Override
        public String getValue(Page page) {
            return locator.apply(page).textContent();
        }
    },
    Price(page -> page.locator("#price")) {
        @Override
        public void setValue(Page page, String value) {
            sliderSet(locator.apply(page), page, value);
        }

        @Override
        public String getValue(Page page) {
            var pence = page.locator("#price span input").getAttribute("value");
            return String.valueOf(Integer.parseInt(pence) / 100);
        }
    },
    OnSale(page -> page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("On Sale"))) {
        @Override
        public void setValue(Page page, String value) {
            var onsaleLocator = locator.apply(page);
            if (value.equals("checked") && !(onsaleLocator.isChecked())) {
                onsaleLocator.click();
            }
        }

        @Override
        public String getValue(Page page) {
            return locator.apply(page).getAttribute("value");
        }
    };

    public abstract void setValue(Page page, String value);

    public abstract String getValue(Page page);

    final Function<Page, Locator> locator;

    FilterOption(Function<Page, Locator> locator) {
        this.locator = locator;
    }

    private static void selectValueFromDropdown(Locator locator, Page page, String value) {
        locator.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(value)).click();
    }

    private static void sliderSet(Locator slider, Page page, String value) {
        double maxPrice = 30.00;

        if (Double.parseDouble(value) > maxPrice)
            throw new Error("Filtered price is greater than max price");

        var percentage = (Float.parseFloat(value) / maxPrice) * 100;

        slider.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        var box = slider.boundingBox();

        if (box == null) throw new Error("Bounding box is null");

        var targetX = box.x + (box.width * (percentage / 100));
        page.mouse().move(box.x + box.width / 2, box.y + box.height / 2);
        page.mouse().down();
        page.mouse().move(targetX, box.y + box.height / 2);
        page.mouse().up();
    }
}

