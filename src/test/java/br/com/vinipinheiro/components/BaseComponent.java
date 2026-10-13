package br.com.vinipinheiro.components;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Superclasse para componentes reutilizáveis de página (Component Object
 * Model).
 * Utiliza busca dinâmica da raiz (rootLocator) para imunidade contra
 * StaleElementReferenceException.
 */
public abstract class BaseComponent {
    protected By rootLocator;
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BaseComponent(By rootLocator, WebDriver driver) {
        this.rootLocator = rootLocator;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public WebElement getRoot() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(rootLocator));
    }

    protected WebElement find(By locator) {
        return getRoot().findElement(locator);
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        WebElement element = waitForClickable(locator);
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void type(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        scrollIntoView(element);
        element.clear();
        element.sendKeys(text);
    }

    protected void selectDropdownByText(By locator, String visibleText) {
        WebElement element = waitForVisibility(locator);
        scrollIntoView(element);
        new Select(element).selectByVisibleText(visibleText);
    }

    protected boolean isVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }
}
