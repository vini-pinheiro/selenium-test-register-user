package br.com.vinipinheiro.pages;

import br.com.vinipinheiro.components.LoginForm;
import br.com.vinipinheiro.components.SignupForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página de autenticação (/login), contendo os componentes LoginForm e SignupForm.
 */
public class LoginPage extends BasePage {
    private final By loginFormLocator = By.cssSelector(".login-form");
    private final By signupFormLocator = By.cssSelector(".signup-form");

    private LoginForm loginForm;
    private SignupForm signupForm;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.loginForm = new LoginForm(driver);
        this.signupForm = new SignupForm(driver);
    }

    public LoginForm getLoginForm() {
        return this.loginForm;
    }

    public SignupForm getSignupForm() {
        return this.signupForm;
    }

    public boolean isPageLoaded() {
        return isElementVisible(loginFormLocator) && isElementVisible(signupFormLocator);
    }
}
