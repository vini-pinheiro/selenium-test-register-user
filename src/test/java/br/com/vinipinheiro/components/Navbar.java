package br.com.vinipinheiro.components;

import br.com.vinipinheiro.pages.DeleteAccountPage;
import br.com.vinipinheiro.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Componente que representa a barra de navegação superior (Navbar).
 */
public class Navbar extends BaseComponent {
    private static final By ROOT = By.cssSelector(".shop-menu");

    private final By homeLink = By.cssSelector(".shop-menu a[href='/']");
    private final By signupLoginLink = By.cssSelector(".shop-menu a[href='/login']");
    private final By deleteAccountLink = By.cssSelector(".shop-menu a[href='/delete_account']");
    private final By loggedInAsLocator = By
            .xpath("//ul[contains(@class, 'navbar-nav')]//li[contains(., 'Logged in as')]");

    public Navbar(WebDriver driver) {
        super(ROOT, driver);
    }

    public Navbar(By rootLocator, WebDriver driver) {
        super(rootLocator, driver);
    }

    public LoginPage clickSignupLogin() {
        click(signupLoginLink);
        return new LoginPage(this.driver);
    }

    public DeleteAccountPage clickDeleteAccount() {
        click(deleteAccountLink);
        return new DeleteAccountPage(this.driver);
    }

    public boolean isLoggedInAs(String username) {
        try {
            WebElement loggedElement = waitForVisibility(loggedInAsLocator);
            return loggedElement.isDisplayed() && loggedElement.getText().contains("Logged in as " + username);
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoggedInText() {
        return getText(loggedInAsLocator);
    }
}
