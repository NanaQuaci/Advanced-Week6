package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BankManagerPage extends BasePage {

    private final By addCustomerTab = By.cssSelector("button[ng-click='addCust()']");
    private final By openAccountTab = By.cssSelector("button[ng-click='openAccount()']");
    private final By customersTab = By.cssSelector("button[ng-click='showCust()']");
    private final By firstName = By.cssSelector("input[ng-model='fName']");
    private final By lastName = By.cssSelector("input[ng-model='lName']");
    private final By postCode = By.cssSelector("input[ng-model='postCd']");
    private final By addButton = By.cssSelector("button[type='submit']");
    private final By customerDropdown = By.id("userSelect");
    private final By currencyDropdown = By.id("currency");

    public BankManagerPage(WebDriver driver) {
        super(driver);
    }

    public boolean addCustomer(String fname, String lname, String postcode) {
        click(addCustomerTab);
        enterText(firstName, fname);
        enterText(lastName, lname);
        enterText(postCode, postcode);
        click(addButton);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            alert.accept();
            return alertText.contains("Customer added");
        } catch (TimeoutException e) {
            return false;
        }
    }


    public boolean createAccount(String customerName, String currency) {
        try {
            click(openAccountTab);

            Select customer = new Select(waitForVisibility(customerDropdown));

            boolean customerExists = customer.getOptions().stream()
                    .anyMatch(option -> option.getText().trim().equalsIgnoreCase(customerName.trim()));

            if (!customerExists) {
                System.out.println("Customer not found in dropdown: " + customerName);
                return false;
            }

            customer.selectByVisibleText(customerName);

            if (currency == null || currency.trim().isEmpty()) {
                System.out.println("Currency not provided, skipping account creation.");
                return false;
            }

            Select currencySelect = new Select(waitForVisibility(currencyDropdown));
            currencySelect.selectByVisibleText(currency);

            click(addButton);

            String alertText = getAlertTextAndAccept();
            System.out.println("Alert Text: " + alertText);

            return alertText.toLowerCase().contains("account created successfully");

        } catch (Exception e) {
            System.out.println("Error during account creation: " + e.getMessage());
            return false;
        }
    }




    public void deleteCustomer(String customerName) {
        click(customersTab);
        WebElement table = waitForVisibility(By.tagName("table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            if (row.getText().contains(customerName)) {
                row.findElement(By.cssSelector("button")).click();
                break;
            }
        }
    }

    public boolean isCustomerPresent(String customerName) {
        click(customersTab);
        return waitForVisibility(By.tagName("table")).getText().contains(customerName);
    }

    public void openCustomerList() {
        click(customersTab);
    }

    public void searchCustomer(String searchTerm) {
        By searchBox = By.cssSelector("input[ng-model='searchCustomer']");
        enterText(searchBox, searchTerm);
    }

    public boolean isCustomerSearchResultVisible(String expectedName) {
        return waitForVisibility(By.tagName("table")).getText().contains(expectedName);
    }

    public void clickOpenAccountTab() {
        driver.findElement(By.xpath("//button[@ng-click='openAccount()']")).click();
    }

}
