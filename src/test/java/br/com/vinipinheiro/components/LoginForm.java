package br.com.vinipinheiro.components;

import br.com.vinipinheiro.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Componente que representa o formulário de Login na página /login ("Login to
 * your account").
 */
public class LoginForm extends BaseComponent {
    private static final By ROOT = By.cssSelector(".login-form");

    private final By titleLocator = By
            .xpath("//div[@class='login-form']//h2[contains(text(), 'Login to your account')]");
    private final By emailLocator = By.cssSelector("input[data-qa='login-email']");
    private final By passwordLocator = By.cssSelector("input[data-qa='login-password']");
    private final By loginButtonLocator = By.cssSelector("button[data-qa='login-button']");
    private final By errorMessageLocator = By
            .xpath("//div[@class='login-form']//p[contains(text(), 'Your email or password is incorrect!')]");

    public LoginForm(WebDriver driver) {
        super(ROOT, driver);
    }

    public LoginForm(By rootLocator, WebDriver driver) {
        super(rootLocator, driver);
    }

    public boolean isTitleVisible() {
        return isVisible(titleLocator);
    }

    public LoginForm fillEmail(String email) {
        type(emailLocator, email);
        return this;
    }

    public LoginForm fillPassword(String password) {
        type(passwordLocator, password);
        return this;
    }

    public void clickLogin() {
        click(loginButtonLocator);
    }

    public HomePage loginSuccessfully(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        clickLogin();
        return new HomePage(this.driver);
    }

    public LoginForm loginWithInvalidCredentials(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        clickLogin();
        return this;
    }

    public boolean isErrorMessageVisible() {
        return isVisible(errorMessageLocator);
    }

    public String getErrorMessage() {
        return getText(errorMessageLocator);
    }
}
