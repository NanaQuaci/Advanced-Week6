package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransactionPage extends BasePage {

    private final By transactionTab = By.cssSelector("button[ng-click='transactions()']");
    private final By transactionTable = By.cssSelector("table");
    private final By resetButton = By.cssSelector("button[ng-click='reset()']");

    public TransactionPage(WebDriver driver) {
        super(driver);
    }

    public void viewTransactions() {
        click(transactionTab);
    }

    public boolean isTransactionTableVisible() {
        return waitForVisibility(transactionTable).isDisplayed();
    }

    public boolean tryToResetTransactions() {
        if (isElementPresent(resetButton)) {
            click(resetButton);
            return true;
        }
        return false;
    }
}
