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
    public void testTransactionHistoryCannotBeReset_CU_007() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();


        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.deposit(validDepositAmount);
        customerPage.deposit(validDepositAmount2);
        customerPage.withdraw(validWithdrawalAmount2);

        TransactionPage transactionPage = new TransactionPage(driver);
        transactionPage.viewTransactions();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        boolean isResetButtonPresent = transactionPage.tryToResetTransactions();
        assertFalse(isResetButtonPresent, "Reset button should not be available for transaction history.");

        boolean cleared = transactionPage.tryToResetTransactions();
        assertFalse(cleared, "Transactions should not be cleared or reset.");
    }
}
