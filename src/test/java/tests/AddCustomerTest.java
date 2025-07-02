// src/test/java/tests/AddCustomerTest.java
package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import pages.BankManagerPage;
import pages.LoginPage;
import base.BaseTest;

import static base.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("XYZ Bank - Manager Functionality")
@Feature("Register Customer")
public class AddCustomerTest extends BaseTest {


    @Test
    @Story("Bank Manager adds a customer")
    @Severity(SeverityLevel.CRITICAL)
    @Description("BM_001 - Ensure a bank manager can add a customer with valid details")
    public void testAddCustomerWithValidData_BM_001() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        boolean alertShown = managerPage.addCustomer(validFirstName, validLastName, validPostCode);

        assertTrue(alertShown, "Customer should be added and alert should be shown.");
    }

    @Test
    @Story("Bank Manager adds invalid customer")
    @Severity(SeverityLevel.NORMAL)
    @Description("BM_002 - Ensure a bank manager cannot add a customer with numbers in their name")
    public void testAddCustomerWithNumbersInName_BM_002() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        boolean alertShown = managerPage.addCustomer(invalidFirstName, validLastName, validPostCode);

        assertFalse(alertShown, "Customer with numeric first name should not be added.");
    }

    @Test
    @Story("Bank Manager adds invalid customer")
    @Severity(SeverityLevel.NORMAL)
    @Description("BM_003 - Ensure a bank manager cannot add a customer with empty fields")
    public void testAddCustomerWithBlankFields_BM_003() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        boolean alertShown = managerPage.addCustomer(validFirstName, "", validPostCode);

        assertFalse(alertShown, "Customer with blank field should not be added.");
    }

    @Test
    @Story("Bank Manager adds invalid customer")
    @Severity(SeverityLevel.NORMAL)
    @Description("BM_008 - Ensure a bank manager cannot add a customer with special characters in name")
    public void testAddCustomerWithSpecialCharacters_BM_008() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        boolean alertShown = managerPage.addCustomer(validFirstName, invalidLastName, validPostCode);

        assertFalse(alertShown, "Customer with special characters in name should not be added.");
    }

    @Test
    @Story("Bank Manager adds invalid customer")
    @Severity(SeverityLevel.NORMAL)
    @Description("BM_009 - Ensure a customer with a valid first name and invalid last name cannot be added")
    public void testAddCustomerWithValidFirstNameInvalidLastName_BM_009() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        boolean alertShown = managerPage.addCustomer(validFirstName, invalidLastName, validPostCode);

        assertFalse(alertShown, "Customer with invalid last name should not be added.");
    }
}
