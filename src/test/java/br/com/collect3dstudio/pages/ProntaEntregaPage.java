package br.com.collect3dstudio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProntaEntregaPage extends BasePage {

    private static final By ITEM_DESCRIPTION = By.className("item-description");
    private static final By ITEM_NAME = By.className("js-item-name");
    private static final By ITEM_PRICE = By.className("item-price");

    public ProntaEntregaPage(WebDriver driver) {
        super(driver);
    }

    /** Products listed on the page as name -> price, in page order. */
    public Map<String, String> getProducts() {
        List<WebElement> items = wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(ITEM_DESCRIPTION, 0));

        Map<String, String> products = new LinkedHashMap<>();
        for (WebElement item : items) {
            // textContent (not getText) so items not currently rendered/visible are still read.
            String name = item.findElement(ITEM_NAME).getAttribute("textContent").trim();
            String price = item.findElement(ITEM_PRICE).getAttribute("textContent").trim();
            if (!name.isEmpty()) {
                products.put(name, price);
            }
        }
        return products;
    }
}
