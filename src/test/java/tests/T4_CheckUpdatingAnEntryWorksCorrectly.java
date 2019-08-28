package tests;

import Base.SqlBaseTest;
import Enums.TestCustomersEnum;
import PageBlocks.SqlPageTableRow;
import Pages.SqlPage;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static Enums.CustomerEntityFields.*;

public class T4_CheckUpdatingAnEntryWorksCorrectly extends SqlBaseTest {
    private static final TestCustomersEnum customerBeforeUpdate = TestCustomersEnum.TEST_CUSTOMER_1;
    private static final TestCustomersEnum customerAfterUpdate = TestCustomersEnum.TEST_CUSTOMER_2;

    private Integer entryId;

    private static final String INSERT_QUERY =
            "INSERT INTO Customers " +
            "('CustomerName', 'ContactName', 'Address', 'City', 'PostalCode', 'Country') " +
            "VALUES ('"+customerBeforeUpdate.getCustomerName()+"', '"+customerBeforeUpdate.getContactName()+"', '"+customerBeforeUpdate.getAddress()+"', '"+customerBeforeUpdate.getCity()+"', '"+customerBeforeUpdate.getPostalCode()+"', '"+customerBeforeUpdate.getCountry()+"');";
    private static final String UPDATE_QUERY = "UPDATE Customers SET " +
            "CustomerName = '"+customerAfterUpdate.getCustomerName()+"', " +
            "ContactName = '"+customerAfterUpdate.getContactName()+"', " +
            "Address = '"+customerAfterUpdate.getAddress()+"', " +
            "City = '"+customerAfterUpdate.getCity()+"', " +
            "PostalCode = '"+customerAfterUpdate.getPostalCode()+"', " +
            "Country = '"+customerAfterUpdate.getCountry()+"' " +
            "WHERE CustomerName = '"+customerBeforeUpdate.getCustomerName()+"';";
    private static final String SELECT_QUERY = "SELECT * FROM Customers;";
    private static final String DELETE_QUERY_FOR_FIRST_CUSTOMER = "DELETE FROM Customers WHERE CustomerName='"+customerBeforeUpdate.getCustomerName()+"';";
    private static final String DELETE_QUERY_FOR_SECOND_CUSTOMER = "DELETE FROM Customers WHERE CustomerName='"+customerAfterUpdate.getCustomerName()+"';";

    @BeforeMethod
    public void beforeTest() {
        removeAllTestEntriesFromDb();
        entryId = Integer.parseInt((new SqlPage())
                .openPage()
                .setSqlQuery(INSERT_QUERY)
                .clickRunSqlButton()
                .setSqlQuery(SELECT_QUERY)
                .clickRunSqlButton()
                .getResultsTable()
                .getRowsByColumnValue(CustomerName.toString(), customerBeforeUpdate.getCustomerName())
                .get(0)
                .getValueForColumn(CustomerID.toString()));
    }

    @AfterMethod
    public void afterTest() {
        removeAllTestEntriesFromDb();
    }

    @Step("Remove test data from DB")
    private void removeAllTestEntriesFromDb() {
        (new SqlPage())
                .openPage()
                .setSqlQuery(DELETE_QUERY_FOR_FIRST_CUSTOMER)
                .clickRunSqlButton()
                .setSqlQuery(DELETE_QUERY_FOR_SECOND_CUSTOMER)
                .clickRunSqlButton();
    }

    @Test(description = "Update an entry in table and check it is updated correctly.")
    public void test() {
        List<SqlPageTableRow> rowsWithCondition =
                (new SqlPage())
                .openPage()
                .setSqlQuery(UPDATE_QUERY)
                .clickRunSqlButton()
                .setSqlQuery(SELECT_QUERY)
                .clickRunSqlButton()
                .getResultsTable()
                .getRowsByColumnValue(CustomerID.toString(), entryId.toString());
        Assert.assertEquals(rowsWithCondition.size(), 1, "There should be only 1 test company in list");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(CustomerName.toString()), customerAfterUpdate.getCustomerName(), "Customer name is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(ContactName.toString()), customerAfterUpdate.getContactName(), "Contanct name is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(Address.toString()), customerAfterUpdate.getAddress(), "Address is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(City.toString()), customerAfterUpdate.getCity(), "City is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(PostalCode.toString()), customerAfterUpdate.getPostalCode(), "Postal code is incorrect.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(Country.toString()), customerAfterUpdate.getCountry(), "Country is incorrect.");
    }
}
