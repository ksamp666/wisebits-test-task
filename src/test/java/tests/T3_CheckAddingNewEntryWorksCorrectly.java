package tests;

import Base.SqlBaseTest;
import PageBlocks.SqlPageTableRow;
import Pages.SqlPage;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static Enums.CustomerEntityFields.*;

public class T3_CheckAddingNewEntryWorksCorrectly extends SqlBaseTest {
    private static final String CUSTOMER_NAME = "Pinguins";
    private static final String CONTACT_NAME = "Anatoliy Zagrebaylo";
    private static final String ADDRESS = "Africa";
    private static final String CITY = "NDjamena";
    private static final String POSTAL_CODE = "777777";
    private static final String COUNTRY = "Chad";

    private static final String INSERT_QUERY = "INSERT INTO Customers ('CustomerName', 'ContactName', 'Address', 'City', 'PostalCode', 'Country') VALUES ('"+CUSTOMER_NAME+"', '"+CONTACT_NAME+"', '"+ADDRESS+"', '"+CITY+"', '"+POSTAL_CODE+"', '"+COUNTRY+"');";
    private static final String SELECT_QUERY = "SELECT * FROM Customers WHERE CustomerName='"+CUSTOMER_NAME+"';";
    private static final String DELETE_QUERY = "DELETE FROM Customers WHERE CustomerName='"+CUSTOMER_NAME+"';";

    @AfterMethod
    public void afterTest() {
        removeAllTestEntriesFromDb();
    }

    @BeforeMethod
    public void beforeTest() {
        removeAllTestEntriesFromDb();
    }

    @Step("Remove test data from DB")
    private void removeAllTestEntriesFromDb() {
        (new SqlPage())
                .openPage()
                .setSqlQuery(DELETE_QUERY)
                .clickRunSqlButton();
    }

    @Test(description = "Add new entry to table and check it is added correctly.")
    public void test() {
        List<SqlPageTableRow> rowsWithCondition =
                (new SqlPage())
                .openPage()
                .setSqlQuery(INSERT_QUERY)
                .clickRunSqlButton()
                .setSqlQuery(SELECT_QUERY)
                .clickRunSqlButton()
                .getResultsTable()
                .getTableRows();
        Assert.assertEquals(rowsWithCondition.size(), 1, "There should be only 1 Pinguins company in list");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(CustomerName.toString()), CUSTOMER_NAME, "Customer name is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(ContactName.toString()), CONTACT_NAME, "Contact name is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(Address.toString()), ADDRESS, "Address is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(City.toString()), CITY, "City is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(PostalCode.toString()), POSTAL_CODE, "Postal code is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(Country.toString()), COUNTRY, "Country is incorrect.");
    }
}
