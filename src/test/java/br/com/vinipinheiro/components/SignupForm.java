package br.com.vinipinheiro.components;

import br.com.vinipinheiro.pages.SignupPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Componente que representa o formulário de cadastro inicial na página /login ("New User Signup!").
 */
public class SignupForm extends BaseComponent {
    private static final By ROOT = By.cssSelector(".signup-form");

    private final By titleLocator = By.xpath("//div[@class='signup-form']//h2[contains(text(), 'New User Signup!')]");
    private final By nameLocator = By.cssSelector("input[data-qa='signup-name']");
    private final By emailLocator = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButtonLocator = By.cssSelector("button[data-qa='signup-button']");

    public SignupForm(WebDriver driver) {
        super(ROOT, driver);
    }

    public SignupForm(By rootLocator, WebDriver driver) {
        super(rootLocator, driver);
    }

    public boolean isTitleVisible() {
        return isVisible(titleLocator);
    }

    public SignupForm fillName(String name) {
        type(nameLocator, name);
        return this;
    }

    public SignupForm fillEmail(String email) {
        type(emailLocator, email);
        return this;
    }

    public SignupPage clickSignup() {
        click(signupButtonLocator);
        return new SignupPage(this.driver);
    }

    public SignupPage signup(String name, String email) {
        fillName(name);
        fillEmail(email);
        return clickSignup();
    }
}

