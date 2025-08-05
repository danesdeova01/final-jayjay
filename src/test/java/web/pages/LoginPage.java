package web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addToCartSauceLabsBackpack;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;

    @FindBy(className = "inventory_item_name")
    private WebElement inventoryItemName;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "first-name")
    private WebElement inputFirstName;

    @FindBy(id = "last-name")
    private WebElement inputLastName;

    @FindBy(id = "postal-code")
    private WebElement inputPostalCode;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "complete-header")
    private WebElement orderConfirmation;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    // Login page actions
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public WebElement getErrorMessageElement() {
        return errorMessage;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    // Menu and logout
    public void clickMenuButton() {
        menuButton.click();
    }

    public void clickLogout() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logoutLink.click();
    }

    // Product & cart
    public void addSauceLabsBackpackToCart() {
        addToCartSauceLabsBackpack.click();
    }

    public WebElement getAddToCartButton() {
        return addToCartSauceLabsBackpack;
    }

    public void clickShoppingCart() {
        shoppingCartLink.click();
    }

    public WebElement getShoppingCartLink() {
        return shoppingCartLink;
    }

    public String getInventoryItemName() {
        return inventoryItemName.getText();
    }

    public WebElement getInventoryItemNameElement() {
        return inventoryItemName;
    }

    // Checkout
    public void clickCheckout() {
        checkoutButton.click();
    }

    public WebElement getCheckoutButton() {
        return checkoutButton;
    }

    public void inputCheckoutInfo(String firstName, String lastName, String postalCode) {
        inputFirstName.clear();
        inputFirstName.sendKeys(firstName);
        inputLastName.clear();
        inputLastName.sendKeys(lastName);
        inputPostalCode.clear();
        inputPostalCode.sendKeys(postalCode);
    }

    public WebElement getInputFirstName() {
        return inputFirstName;
    }

    public WebElement getInputLastName() {
        return inputLastName;
    }

    public WebElement getInputPostalCode() {
        return inputPostalCode;
    }

    public void clickContinue() {
        continueButton.click();
    }

    public WebElement getContinueButton() {
        return continueButton;
    }

    public void clickFinish() {
        finishButton.click();
    }

    public WebElement getFinishButton() {
        return finishButton;
    }

    // Order confirmation

    public String getOrderConfirmationText() {
        return orderConfirmation.getText();
    }

    public WebElement getOrderConfirmation() {
        return orderConfirmation;
    }
}
