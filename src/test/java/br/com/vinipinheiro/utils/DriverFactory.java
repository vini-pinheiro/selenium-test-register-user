package br.com.vinipinheiro.utils;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Fábrica de instâncias do WebDriver com configurações otimizadas para testes E2E.
 */
public class DriverFactory {

    public static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();

        // Configurações para estabilidade e contorno de overlays
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");

        // Suporte a modo headless opcional via flag de execução: -Dheadless=true
        String headlessProperty = System.getProperty("headless");
        if ("true".equalsIgnoreCase(headlessProperty)) {
            options.addArguments("--headless=new");
        }

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        return driver;
    }
}

