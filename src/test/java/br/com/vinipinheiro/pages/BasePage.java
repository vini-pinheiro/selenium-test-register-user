package br.com.vinipinheiro.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Superclasse para todas as páginas (Page Object Model),
 * centralizando esperas explícitas, interações seguras e mitigação de riscos de UI (como anúncios).
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    protected WebElement waitForVisibility(By locator) {
        dismissAdsIfPresent();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        dismissAdsIfPresent();
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        dismissAdsIfPresent();
        WebElement element = waitForClickable(locator);
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            // Fallback resiliente via JavaScript caso clique seja interceptado
            jsClick(element);
        }
        handleGoogleVignetteIfPresent();
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
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
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    protected boolean isElementVisible(By locator) {
        try {
            dismissAdsIfPresent();
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    /**
     * Remove sobreposições e iframes de publicidade do Google que possam interceptar cliques.
     */
    public void dismissAdsIfPresent() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "const ads = document.querySelectorAll('iframe[id^=\"aswift_\"], iframe[src*=\"google\"], div[id^=\"google_ads\"], .grippy-host');" +
                "ads.forEach(ad => ad.remove());"
            );
        } catch (Exception ignored) {
        }
    }

    /**
     * Trata o caso em que a URL é interceptada por anúncios do tipo vignette (#google_vignette).
     */
    public void handleGoogleVignetteIfPresent() {
        try {
            if (driver.getCurrentUrl().contains("#google_vignette")) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.history.back();");
            }
        } catch (Exception ignored) {
        }
    }
}
