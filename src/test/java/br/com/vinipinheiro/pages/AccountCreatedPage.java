package br.com.vinipinheiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Página de confirmação de conta criada (/account_created - "ACCOUNT CREATED!").
 */
public class AccountCreatedPage extends BasePage {
    private final By accountCreatedTitle = By.cssSelector("h2[data-qa='account-created']");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");

    public AccountCreatedPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountCreatedVisible() {
        return isElementVisible(accountCreatedTitle);
    }

    public HomePage clickContinue() {
        click(continueButton);
        return new HomePage(driver);
    }
}
