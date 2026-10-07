package br.com.collect3dstudio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    // The site's menu no longer has a "Catálogo" dropdown; "Produtos a venda" links straight to /pronta-entrega/.
    private static final By PRONTA_ENTREGA_LINK = By.cssSelector("a.nav-list-link[href*='/pronta-entrega']");

    // Locators taken from the site's HTML (Nuvemshop "Rio" theme, Swiper carousel).
    private static final By SEARCH_ICON = By.cssSelector("header a.js-search-button");
    private static final By USER_ICON = By.cssSelector("header a[href='/account/login/']");
    private static final By CART_ICON = By.cssSelector("header #ajax-cart a");
    private static final By SEARCH_INPUT = By.cssSelector("#nav-search input[name='q']");
    private static final By CART_AMOUNT = By.cssSelector("header .js-cart-widget-amount");
    private static final By HERO_SLIDE_IMAGE = By.cssSelector(".js-home-main-slider-visibility .swiper-slide img.slider-image");
    private static final By CAROUSEL_PREV = By.cssSelector(".js-swiper-home-prev");
    private static final By CAROUSEL_NEXT = By.cssSelector(".js-swiper-home-next");
    private static final By WHATSAPP_BUTTON = By.cssSelector("a.btn-whatsapp");

    private final String baseUrl;

    public HomePage(WebDriver driver, String baseUrl) {
        super(driver);
        this.baseUrl = baseUrl;
    }

    public HomePage open() {
        driver.get(baseUrl);
        return this;
    }

    public ProntaEntregaPage clickProntaEntrega() {
        wait.until(ExpectedConditions.elementToBeClickable(PRONTA_ENTREGA_LINK)).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(baseUrl)));
        return new ProntaEntregaPage(driver);
    }

    public ProntaEntregaPage goToProntaEntrega() {
        return clickProntaEntrega();
    }

    public boolean isMenuItemVisible(String label) {
        return isDisplayed(By.partialLinkText(label));
    }

    public HomePage clickMenuItem(String label, String expectedUrlPart) {
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(label))).click();
        wait.until(ExpectedConditions.urlContains(expectedUrlPart));
        return this;
    }

    public HomePage search(String term) {
        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_ICON)).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        input.sendKeys(term, Keys.ENTER);
        wait.until(ExpectedConditions.urlContains("/search/"));
        return this;
    }

    public boolean isSearchInputVisible() {
        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_ICON)).click();
        return isDisplayed(SEARCH_INPUT);
    }

    // The hero text and the "Janeiro - 2027" button are part of the banner images, not DOM text.
    public boolean isHeroBannerVisible() {
        return isDisplayed(HERO_SLIDE_IMAGE);
    }

    public String getCartAmount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CART_AMOUNT)).getText().trim();
    }

    public boolean isSearchIconVisible() {
        return isDisplayed(SEARCH_ICON);
    }

    public boolean isUserIconVisible() {
        return isDisplayed(USER_ICON);
    }

    public boolean isCartIconVisible() {
        return isDisplayed(CART_ICON);
    }

    public boolean isCarouselPrevVisible() {
        return isDisplayed(CAROUSEL_PREV);
    }

    public boolean isCarouselNextVisible() {
        return isDisplayed(CAROUSEL_NEXT);
    }

    public boolean isWhatsappButtonVisible() {
        return isDisplayed(WHATSAPP_BUTTON);
    }

    private boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
