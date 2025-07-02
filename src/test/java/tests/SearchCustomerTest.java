// src/test/java/tests/SearchCustomerTest.java
package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import pages.BankManagerPage;
import pages.LoginPage;
import base.BaseTest;
import io.qameta.allure.*;

import static base.TestData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("XYZ Bank - Manager Functionality")
@Feature("Search Customer")
public class SearchCustomerTest extends BaseTest {

    @Test
    @Story("Bank Manager searches customer")
    @Severity(SeverityLevel.NORMAL)
    @Description("BM_007 - Ensure the search function displays a matching customer")
    public void testSearchCustomerByFirstName_BM_007() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToHomePage();
        loginPage.clickBankManagerLogin();

        BankManagerPage managerPage = new BankManagerPage(driver);
        managerPage.openCustomerList();
        managerPage.searchCustomer(searchCustomerName);

        boolean result = managerPage.isCustomerSearchResultVisible(searchCustomerName);
        assertTrue(result, "Search results should contain the name 'Potter'.");
    }
}
