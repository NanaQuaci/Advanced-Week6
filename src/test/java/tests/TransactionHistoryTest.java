package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CustomerPage;
import pages.LoginPage;
import pages.TransactionPage;
import base.BaseTest;
import io.qameta.allure.*;

import java.time.Duration;

import static base.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("XYZ Bank - Customer Functionality")
@Feature("Transaction History")
public class TransactionHistoryTest extends BaseTest {

    @Test
    @Story("Customer views transactions")
    @Severity(SeverityLevel.NORMAL)
    @Description("CU_006 - Ensure customer can view a list of previous transactions")
    public void testViewTransactionHistory_CU_006() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.deposit(validDepositAmount);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        TransactionPage transactionPage = new TransactionPage(driver);
        transactionPage.viewTransactions();

        assertTrue(transactionPage.isTransactionTableVisible(), "Transaction table should be visible.");
    }

    @Test
    @Story("Customer resets transactions")
    @Severity(SeverityLevel.NORMAL)
    @Description("CU_007 - Ensure customer cannot reset list of previous transactions")
    public void testTransactionHistoryCannotBeReset_CU_007() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.deposit(validDepositAmount);
        customerPage.deposit(validDepositAmount2);

        TransactionPage transactionPage = new TransactionPage(driver);
        transactionPage.viewTransactions();

        assertFalse(transactionPage.isResetButtonPresent(), "Reset button should not be available for transaction history.");
    }

    @Test
    @Story("Customer reset restriction")
    @Severity(SeverityLevel.CRITICAL)
    @Description("CU_007_NEG - Ensure that clicking reset does not clear transaction history if reset is wrongly available")
    public void testResetTransactionHistoryIsBlocked_CU_007_NEG() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.deposit(validDepositAmount);
        customerPage.deposit(validDepositAmount2);

        TransactionPage transactionPage = new TransactionPage(driver);
        transactionPage.viewTransactions();

        // If reset button exists, click it
        if (transactionPage.isResetButtonPresent()) {
            transactionPage.clickResetButton();
            Thread.sleep(3000);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        // Verify that transactions are still visible
        new WebDriverWait(driver, Duration.ofSeconds(5));
        assertFalse(transactionPage.areTransactionsCleared(),
                "Transactions should not be cleared even if reset button is clicked.");
    }


}
