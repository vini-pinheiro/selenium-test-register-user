package br.com.vinipinheiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Página de confirmação de exclusão de conta (/delete_account - "ACCOUNT DELETED!").
 */
public class DeleteAccountPage extends BasePage {
    private final By accountDeletedTitle = By.cssSelector("h2[data-qa='account-deleted']");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");

    public DeleteAccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountDeletedVisible() {
        return isElementVisible(accountDeletedTitle);
    }

    public HomePage clickContinue() {
        click(continueButton);
        return new HomePage(driver);
    }
}
