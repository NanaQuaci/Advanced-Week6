package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BankManagerPage;
import pages.LoginPage;
import base.BaseTest;
import io.qameta.allure.*;

import java.time.Duration;

import static base.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("XYZ Bank - Manager Functionality")
@Feature("Create Bank Account for Customer")
public class CreateAccountTest extends BaseTest {

    @Test
    public void testAddCustomerAndCreateAccountForAddedCustomer_BM_004() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        managerPage.addCustomer(validFirstName, validLastName, validPostCode);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        managerPage.clickOpenAccountTab();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//select[@id='userSelect']"), validFullName));


        boolean success = managerPage.createAccount(validFullName, "Dollar");

        assertTrue(success, "Account created successfully.");
    }


    @Test
    public void testAddAndCreateAccountWithCurrencyBlank_BM_005() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        managerPage.addCustomer(validFirstName, validLastName, validPostCode);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        managerPage.clickOpenAccountTab();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//select[@id='userSelect']"), validFullName));
        boolean success = managerPage.createAccount(validFullName, "");

        assertFalse(success, "Account creation should fail if currency is blank.");
    }


    @Test
    public void testCreateAccountWithoutAddingCustomer() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        boolean success = managerPage.createAccount(validFullName, "Dollar");

        assertFalse(success, "Account should not be created for a non-existing customer.");
    }

    @Test
    public void testCreateAccountWithoutAddingCustomerAndNoCurrency() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        boolean success = managerPage.createAccount(validFullName, "");

        assertFalse(success, "Account should not be created for a non-existing customer.");
    }




}
