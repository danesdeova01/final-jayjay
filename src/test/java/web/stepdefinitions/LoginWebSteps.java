package web.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import web.pages.LoginPage;
import org.junit.Assert;

public class LoginWebSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;

    @Before("@web or @api")
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless",
                "--disable-gpu",
                "--window-size=1920,1080",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-extensions",
                "--ignore-certificate-errors"
        );
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // timeout 15 detik
        loginPage = new LoginPage(driver);
    }

    @After("@web or @api")
    public void teardown() {
        if (driver != null) driver.quit();
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.visibilityOf(loginPage.getLoginButton()));
    }

    @When("I enter valid username and password")
    public void i_enter_valid_username_and_password() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
    }

    @Then("I should see the products page")
    public void i_should_see_the_products_page() {
        wait.until(ExpectedConditions.titleContains("Swag Labs"));
        Assert.assertTrue(driver.getTitle().contains("Swag Labs"));
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @When("I enter invalid username and password")
    public void i_enter_invalid_username_and_password() {
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("wrong_pass");
        loginPage.clickLogin();
    }

    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        wait.until(ExpectedConditions.visibilityOf(loginPage.getErrorMessageElement()));
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains("Username and password do not match")
                || actualMessage.contains("Epic sadface"));
    }

    @Given("I am logged in as a valid user")
    public void i_am_logged_in_as_a_valid_user() {
        i_am_on_the_login_page();
        i_enter_valid_username_and_password();

        wait.until(ExpectedConditions.urlContains("inventory.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @When("I click logout button")
    public void i_click_logout_button() {
        loginPage.clickMenuButton();
        loginPage.clickLogout();
    }

    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() {
        wait.until(ExpectedConditions.urlContains("saucedemo.com"));
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
    }

    @When("I add a product to cart")
    public void i_add_a_product_to_cart() {
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.getAddToCartButton()));
        loginPage.addSauceLabsBackpackToCart();
    }

    @Then("the cart should contain the product")
    public void the_cart_should_contain_the_product() {
        loginPage.clickShoppingCart();
        wait.until(ExpectedConditions.visibilityOf(loginPage.getInventoryItemNameElement()));
        String productName = loginPage.getInventoryItemName();
        Assert.assertTrue(productName.toLowerCase().contains("backpack"));
    }

    @When("I proceed to checkout with valid information")
    public void i_proceed_to_checkout_with_valid_information() {
        loginPage.clickShoppingCart();

        wait.until(ExpectedConditions.elementToBeClickable(loginPage.getCheckoutButton())).click();

        wait.until(ExpectedConditions.visibilityOf(loginPage.getInputFirstName()));

        loginPage.getInputFirstName().clear();
        loginPage.getInputFirstName().sendKeys("Test");

        loginPage.getInputLastName().clear();
        loginPage.getInputLastName().sendKeys("User");

        loginPage.getInputPostalCode().clear();
        loginPage.getInputPostalCode().sendKeys("12345");

        loginPage.clickContinue();

        wait.until(ExpectedConditions.elementToBeClickable(loginPage.getFinishButton())).click();

        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    @Then("I should see the order confirmation")
    public void i_should_see_the_order_confirmation() {
        wait.until(ExpectedConditions.visibilityOf(loginPage.getOrderConfirmation()));
        String confirmationText = loginPage.getOrderConfirmationText();
        Assert.assertTrue(confirmationText.toLowerCase().contains("thank you"));
    }
}
