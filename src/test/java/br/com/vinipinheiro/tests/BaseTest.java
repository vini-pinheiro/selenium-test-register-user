package br.com.vinipinheiro.tests;

import br.com.vinipinheiro.utils.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 * Superclasse para gerenciamento do ciclo de vida dos testes E2E com JUnit 5.
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected final String baseUrl = "https://automationexercise.com/";

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(baseUrl);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

