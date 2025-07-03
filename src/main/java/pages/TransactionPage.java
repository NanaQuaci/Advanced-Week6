package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TransactionPage extends BasePage {

    private final By transactionTab = By.cssSelector("button[ng-click='transactions()']");
    private final By transactionTable = By.cssSelector("table");
    private final By resetButton = By.cssSelector("button[ng-click='reset()']");
    private final By transactionRows = By.cssSelector("table tbody tr"); // adjust if needed



    public TransactionPage(WebDriver driver) {
        super(driver);
    }

    public void viewTransactions() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(transactionTab));
        driver.findElement(transactionTab).click();  // ✅ always fresh element
    }

    public boolean isTransactionTableVisible() {
        return waitForVisibility(transactionTable).isDisplayed();
    }

    public boolean isResetButtonPresent() {
        try {
            WebElement reset = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(resetButton));
            return reset.isDisplayed() && reset.isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }


    public boolean areTransactionsCleared() {
        List<WebElement> rows = driver.findElements(transactionRows);
        return rows.isEmpty();
    }

    public void clickResetButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement reset = wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        reset.click();
        Thread.sleep(2000);
    }


}
