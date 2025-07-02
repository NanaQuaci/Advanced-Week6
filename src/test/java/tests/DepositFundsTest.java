package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import pages.CustomerPage;
import pages.LoginPage;
import base.BaseTest;
import io.qameta.allure.*;

import static base.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("XYZ Bank - Customer Functionality")
@Feature("Deposit Money")
public class DepositFundsTest extends BaseTest {

    @Test
    @Story("Customer deposits money")
    @Severity(SeverityLevel.CRITICAL)
    @Description("CU_001 - Ensure a logged-in customer can deposit a valid amount")
    public void testDepositWithValidAmount_CU_001() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.deposit(validDepositAmount);

        String message = customerPage.getSuccessMessage();
        assertTrue(message.contains("Deposit Successful"), "Expected success message not displayed.");
    }

    @Test
    @Story("Customer deposits invalid amount")
    @Severity(SeverityLevel.NORMAL)
    @Description("CU_002 - Ensure error is shown for deposit of invalid amount")
    public void testDepositWithInvalidAmount_CU_002() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.deposit(invalidAmount);

        String message = "";
        try {
            message = customerPage.getSuccessMessage();
        } catch (TimeoutException e) {
            message = ""; // No message appeared
        }
        assertTrue(message.isEmpty() || !message.contains(""), "Deposit should not be successful.");

    }

    @Test
    @Story("Customer deposits invalid amount")
    @Severity(SeverityLevel.NORMAL)
    @Description("CU_008 - Ensure error is shown for deposit of invalid amount")
    public void testErrorPopupForInvalidDeposit_CU_008() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickCustomerLogin();
        loginPage.selectCustomer(availableCustomerName);
        loginPage.clickLogin();

        CustomerPage customerPage = new CustomerPage(driver);
        customerPage.deposit(invalidAmount);

        // Try to get error element without throwing exception
        boolean errorVisible = customerPage.isErrorMessageVisible();

        // Fails the test since no error message or popup showed
        assertTrue(errorVisible, "Error message or popup should be displayed for invalid deposit.");
    }

}
