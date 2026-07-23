package com.glos.tests;

import com.glos.config.Config;
import com.glos.driver.DriverFactory;
import com.glos.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    private WebDriver driver;

    @BeforeMethod
    public void startBrowser() {
        driver = DriverFactory.create();
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        if (driver != null) driver.quit();
    }

    @Test
    public void userCanSignInToGlosQa() {
        LoginPage login = new LoginPage(driver, DriverFactory.waitFor(driver));
        login.open(Config.get("baseUrl", "https://qa.glosonline.com/"));
        login.signIn(Config.required("username"), Config.required("password"));
        Assert.assertTrue(login.isSignedIn(), "The GLOS QA user was not redirected to an authenticated page.");
    }
}
