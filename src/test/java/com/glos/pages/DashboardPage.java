package com.glos.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Authenticated GLOS dashboard actions. */
public final class DashboardPage {
    private static final By THEME_TOGGLE = By.xpath("//button[@class='theme-toggle-btn']");
    private static final By AC_OPEN = By.xpath("//button[@class='ac-open-btn']");
    private static final By WELCOME_BANNER = By.xpath(
            "//*[contains(@class, 'welcome-banner') or contains(@class, 'welcome')][normalize-space()]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void toggleTheme() {
        wait.until(ExpectedConditions.elementToBeClickable(THEME_TOGGLE)).click();
    }

    public void openAcPanel() {
        wait.until(ExpectedConditions.elementToBeClickable(AC_OPEN)).click();
    }

    public boolean isWelcomeBannerDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(currentDriver ->
                    currentDriver.findElements(WELCOME_BANNER).stream().anyMatch(WebElement::isDisplayed));
            return true;
        } catch (org.openqa.selenium.TimeoutException ignored) {
            return false;
        }
    }
}
