package br.com.vinipinheiro.pages;

import br.com.vinipinheiro.components.Navbar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Página principal (HomePage), integrando o componente Navbar e verificações da tela inicial.
 */
public class HomePage extends BasePage {
    private final By sliderCarouselLocator = By.id("slider-carousel");
    private final By mainLogoLocator = By.cssSelector("img[alt='Website for automation practice']");

    private Navbar navbar;

    public HomePage(WebDriver driver) {
        super(driver);
        this.navbar = new Navbar(driver);
    }

    public Navbar getNavbar() {
        return this.navbar;
    }

    public boolean isHomePageVisible() {
        return isElementVisible(sliderCarouselLocator) || isElementVisible(mainLogoLocator);
    }

    public LoginPage clickSignupLogin() {
        return navbar.clickSignupLogin();
    }

    public DeleteAccountPage clickDeleteAccount() {
        return navbar.clickDeleteAccount();
    }

    public boolean isLoggedInAs(String username) {
        return navbar.isLoggedInAs(username);
    }
}
