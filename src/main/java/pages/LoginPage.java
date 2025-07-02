package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends BasePage {

    private final By customerLoginBtn = By.cssSelector("button[ng-click='customer()']");
    private final By bankManagerLoginBtn = By.cssSelector("button[ng-click='manager()']");
    private final By customerDropdown = By.id("userSelect");
    private final By loginBtn = By.cssSelector("button[type='submit']");

    
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void goToLoginPage() {
        navigateToHomePage();
    }

    public void clickBankManagerLogin() {
        click(bankManagerLoginBtn);
    }

    public void clickCustomerLogin() {
        click(customerLoginBtn);
    }

    public void selectCustomer(String customerName) {
        Select dropdown = new Select(waitForVisibility(customerDropdown));
        dropdown.selectByVisibleText(customerName);
    }

    public void clickLogin() {
        click(loginBtn);
    }
}
