package tests;

import Base.SqlBaseTest;
import Enums.TestCustomersEnum;
import PageBlocks.SqlPageTableRow;
import Pages.SqlPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static Enums.CustomerEntityFields.*;

public class T5_CheckThatUsaHasMoreEntriesThanAnyOtherCountry extends SqlBaseTest {
    private static final String COUNT_FIELD = "COUNT(CustomerID)";
    private static final String QUERY = "SELECT COUNT(CustomerID), Country FROM Customers GROUP BY Country";

    @Test(description = "Check that USA has more entries than any other country.")
    public void test() {
        List<SqlPageTableRow> rowsWithCondition =
                (new SqlPage())
                .openPage()
                .setSqlQuery(QUERY)
                .clickRunSqlButton()
                .getResultsTable()
                .getTableRows();

        SqlPageTableRow usaRow = rowsWithCondition.stream().filter(row -> row.getValueForColumn(Country.toString()).equals("USA")).findFirst().orElse(null);
        Assert.assertNotNull(usaRow, "USA country not found in table.");

        int usaEntriesNumber = Integer.parseInt(usaRow.getValueForColumn(COUNT_FIELD));
        rowsWithCondition.forEach(row -> {
            if (!row.getValueForColumn(Country.toString()).equals("USA")) {
                Assert.assertTrue(Integer.parseInt(row.getValueForColumn(COUNT_FIELD)) < usaEntriesNumber, "Oh no! " + row.getValueForColumn(Country.toString()) + " has more entries than USA!");
            }
        });
    }
}
