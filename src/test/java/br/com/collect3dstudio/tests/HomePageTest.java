package br.com.collect3dstudio.tests;

import br.com.collect3dstudio.base.BaseTest;
import br.com.collect3dstudio.data.ProntaEntregaProduct;
import br.com.collect3dstudio.pages.HomePage;
import br.com.collect3dstudio.pages.ProntaEntregaPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HomePageTest extends BaseTest {

    @Test
    void shouldOpenHomePage() {
        HomePage homePage = new HomePage(driver, BASE_URL).open();

        assertTrue(homePage.getCurrentUrl().contains("collect3dstudio.com.br"),
                "Unexpected URL: " + homePage.getCurrentUrl());
        assertFalse(homePage.getTitle().isBlank(), "Page title should not be empty");
    }

    @Test
    void shouldDisplayMainMenuItems() {
        HomePage homePage = new HomePage(driver, BASE_URL).open();

        for (String item : List.of("Início", "Produtos a venda", "Catálogo", "Orçamentos", "Contato")) {
            assertTrue(homePage.isMenuItemVisible(item), "Menu item not visible: " + item);
        }
    }

    @ParameterizedTest(name = "menu \"{0}\" navigates to {1}")
    @CsvSource({
            "Produtos a venda, /pronta-entrega/",
            "Catálogo sob encomenda, /sob-encomenda/",
            "Orçamentos, /como-comprar/",
            "Contato, /contato/"
    })
    void shouldNavigateFromMenuItem(String label, String expectedPath) {
        HomePage homePage = new HomePage(driver, BASE_URL).open()
                .clickMenuItem(label, expectedPath);

        assertTrue(homePage.getCurrentUrl().contains(expectedPath),
                "Unexpected URL: " + homePage.getCurrentUrl());
    }

    @Test
    void shouldReturnToHomeFromInicioMenuItem() {
        HomePage homePage = new HomePage(driver, BASE_URL).open()
                .clickMenuItem("Contato", "/contato/")
                .clickMenuItem("Início", "collect3dstudio.com.br");

        assertFalse(homePage.getCurrentUrl().contains("/contato/"),
                "Should have left the contact page: " + homePage.getCurrentUrl());
    }

    @Test
    void shouldShowEmptyCartCounter() {
        HomePage homePage = new HomePage(driver, BASE_URL).open();

        assertEquals("0", homePage.getCartAmount(), "Cart counter should be 0 for a new session");
    }

    @Test
    void shouldOpenSearchInput() {
        HomePage homePage = new HomePage(driver, BASE_URL).open();

        assertTrue(homePage.isSearchInputVisible(), "Search input should appear after clicking the search icon");
    }

    @Test
    void shouldSearchForTerm() {
        HomePage homePage = new HomePage(driver, BASE_URL).open().search("vincent");

        assertTrue(homePage.getCurrentUrl().contains("/search/"),
                "Unexpected URL: " + homePage.getCurrentUrl());
        assertTrue(homePage.getCurrentUrl().contains("q=vincent"),
                "Search term missing from URL: " + homePage.getCurrentUrl());
    }

    @Test
    void shouldDisplayHeaderIcons() {
        HomePage homePage = new HomePage(driver, BASE_URL).open();

        assertTrue(homePage.isSearchIconVisible(), "Search icon should be visible");
        assertTrue(homePage.isUserIconVisible(), "User icon should be visible");
        assertTrue(homePage.isCartIconVisible(), "Cart icon should be visible");
    }

    @Test
    void shouldDisplayHeroBannerContent() {
        HomePage homePage = new HomePage(driver, BASE_URL).open();

        assertTrue(homePage.isHeroBannerVisible(), "Hero banner image should be visible");
        assertTrue(homePage.isCarouselPrevVisible(), "Carousel previous arrow should be visible");
        assertTrue(homePage.isCarouselNextVisible(), "Carousel next arrow should be visible");
    }

    @Test
    void shouldDisplayWhatsappButton() {
        HomePage homePage = new HomePage(driver, BASE_URL).open();

        assertTrue(homePage.isWhatsappButtonVisible(), "Floating WhatsApp button should be visible");
    }

    @Test
    void shouldOpenProntaEntregaFromCatalogoMenu() {
        ProntaEntregaPage prontaEntregaPage = new HomePage(driver, BASE_URL)
                .open()
                .goToProntaEntrega();

        assertTrue(prontaEntregaPage.getCurrentUrl().toLowerCase().contains("pronta-entrega"),
                "Unexpected URL: " + prontaEntregaPage.getCurrentUrl());

        Map<String, String> products = prontaEntregaPage.getProducts();
        products.forEach((name, price) -> System.out.println(name + " - " + price));

        assertEquals(ProntaEntregaProduct.asMap(), products,
                "Products (name -> price) differ from the expected list");
    }
}
