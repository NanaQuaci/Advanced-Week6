package pages;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomerPage extends BasePage {

    private final By depositTab = By.cssSelector("button[ng-click='deposit()']");
    private final By withdrawTab = By.cssSelector("button[ng-click='withdrawl()']");
    private final By amountField = By.cssSelector("input[ng-model='amount']");
    private final By submitButton = By.cssSelector("button[type='submit']");
    private final By logoutButton = By.cssSelector("button[ng-click='byebye()']");
    private final By successMessage = By.cssSelector(".error");

    public CustomerPage(WebDriver driver) {
        super(driver);
    }

    private void performTransaction(By tab, String amount) {
        click(tab);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(amountField));
        wait.until(ExpectedConditions.elementToBeClickable(amountField));

        WebElement amountInput = driver.findElement(amountField);
        amountInput.clear();
        amountInput.sendKeys(amount);

        click(submitButton);
    }

    @Step("Depositing amount: {amount}")
    public void deposit(String amount) {
        performTransaction(depositTab, amount);
    }

    @Step("Withdrawing amount: {amount}")
    public void withdraw(String amount) {
        performTransaction(withdrawTab, amount);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    public void logout() {
        click(logoutButton);
    }

    public boolean isErrorMessageVisible() {
        try {
            WebElement errorElement = waitForVisibility(By.cssSelector(".error"), 3);
            return errorElement.isDisplayed();
        } catch (TimeoutException e) {
            return false; // No error message found
        }
    }

    public void clickWithdrawTab() {
        click(withdrawTab);
    }


}
