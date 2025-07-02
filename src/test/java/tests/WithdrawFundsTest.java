// src/test/java/tests/WithdrawFundsTest.java
package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CustomerPage;
import pages.LoginPage;
import base.BaseTest;
import io.qameta.allure.*;

import java.time.Duration;


import static base.TestData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("XYZ Bank - Customer Functionality")
@Feature("Withdraw Money")
public class WithdrawFundsTest extends BaseTest {

    @Test
    @Story("Customer withdraws valid amount")
    @Severity(SeverityLevel.CRITICAL)
    @Description("CU_003 - Ensure a logged-in customer can withdraw a valid amount after deposit")
    public void testWithdrawWithValidAmount_CU_003() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.deposit(validDepositAmount);
        customerPage.clickWithdrawTab();
        customerPage.withdraw(validWithdrawalAmount);

        String message = customerPage.getSuccessMessage();
        assertTrue(message.contains("Transaction successful"), "Expected successful withdrawal message.");
    }

    @Test
    @Story("Customer withdraws invalid amount")
    @Severity(SeverityLevel.NORMAL)
    @Description("CU_004 - Ensure error is shown when customer tries to withdraw a negative amount")
    public void testWithdrawWithInvalidAmount_CU_004() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.withdraw(invalidAmount);

        String message = "";
        try {
            message = customerPage.getSuccessMessage();
        } catch (TimeoutException e) {
            message = "";
        }
        assertTrue(message.isEmpty() || !message.contains(""), "Withdrawal should not be successful.");
    }

    @Test
    @Story("Customer withdraws money")
    @Severity(SeverityLevel.NORMAL)
    @Description("CU_005 - Ensure customer cannot withdraw more than available balance")
    public void testWithdrawMoreThanBalance_CU_005() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.withdraw(hugeWithdrawalAmount);

        String message = customerPage.getSuccessMessage();
        assertTrue(message.contains("Transaction Failed") || message.isEmpty(), "Overdrawn withdrawal should fail.");
    }

    @Test
    public void testErrorPopupForInvalidWithdraw_CU_009() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.withdraw(negativeWithdrawalAmount);

        boolean errorVisible = customerPage.isErrorMessageVisible();

        assertTrue(errorVisible, "Test failed: No error message displayed for negative withdrawal, but it should.");
    }
}
