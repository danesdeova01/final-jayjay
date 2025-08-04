package web.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class LoginWebSteps {
    WebDriver driver;
    LoginPage loginPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        driver.get("https://www.saucedemo.com/");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
    }

    @When("I enter valid username and password")
    public void iEnterValidUsernameAndPassword() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @When("I enter invalid username and password")
    public void iEnterInvalidUsernameAndPassword() {
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("wrong_pass");
        loginPage.clickLogin();
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains("Username and password do not match")
                || actualMessage.contains("Epic sadface"));
    }

    @Given("I am logged in as a valid user")
    public void iAmLoggedInAsAValidUser() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        // Tunggu sampai halaman inventory benar-benar loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @When("I click logout button")
    public void iClickLogoutButton() {
        loginPage.clickMenuButton();
        loginPage.clickLogout();
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("saucedemo.com"));

        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
    }

    @When("I add a product to cart")
    public void i_add_a_product_to_cart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack")));
        addToCartBtn.click();
    }

    @Then("the cart should contain the product")
    public void the_cart_should_contain_the_product() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.className("shopping_cart_link")).click();
        WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
        Assert.assertTrue(productName.getText().toLowerCase().contains("backpack"));
    }

    @When("I proceed to checkout with valid information")
    public void i_proceed_to_checkout_with_valid_information() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Klik ikon keranjang belanja
        driver.findElement(By.className("shopping_cart_link")).click();

        // Klik tombol Checkout saat clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();

        // Tunggu form input checkout (first-name) tampil dan isi form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("12345");

        // Klik Continue dan tunggu halaman berikutnya siap
        driver.findElement(By.id("continue")).click();

        // Tunggu tombol Finish clickable lalu klik
        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();

        // Tunggu sampai halaman selesai load penuh menggunakan JS readyState
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    @Then("I should see the order confirmation")
    public void i_should_see_the_order_confirmation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        String confirmationText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header"))).getText();
        Assert.assertTrue(confirmationText.toLowerCase().contains("thank you"));
    }
}
