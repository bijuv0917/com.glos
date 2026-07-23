package com.glos.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Login flow hosted by Clerk on the GLOS QA sign-in route. */
public final class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open(String baseUrl) {
        driver.get(baseUrl.replaceAll("/+$", "") + "/signin");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("signin"),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input"))));
    }

    public void signIn(String username, String password) {
        typeFirstVisible(List.of(
                By.cssSelector("input[name='identifier']"),
                By.cssSelector("input[type='email']"),
                By.cssSelector("input[type='text']")), username);
        clickFirstVisible(List.of(
                By.cssSelector("button[type='submit']"),
                By.xpath("//button[normalize-space()='Continue']")));

        typeFirstVisible(List.of(
                By.cssSelector("input[name='password']"),
                By.cssSelector("input[type='password']")), password);
        clickFirstVisible(List.of(
                By.cssSelector("button[type='submit']"),
                By.xpath("//button[contains(normalize-space(), 'Continue') or contains(normalize-space(), 'Sign in') ]")));
    }

    public boolean isSignedIn() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("dashboard"),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'workspace') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'dashboard')]"))));
            return true;
        } catch (org.openqa.selenium.TimeoutException ignored) {
            return false;
        }
    }

    private void typeFirstVisible(List<By> locators, String value) {
        WebElement element = wait.until(driver -> locators.stream()
                .flatMap(locator -> driver.findElements(locator).stream())
                .filter(WebElement::isDisplayed).findFirst().orElse(null));
        element.clear();
        element.sendKeys(value);
    }

    private void clickFirstVisible(List<By> locators) {
        WebElement element = wait.until(driver -> locators.stream()
                .flatMap(locator -> driver.findElements(locator).stream())
                .filter(WebElement::isDisplayed).findFirst().orElse(null));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
