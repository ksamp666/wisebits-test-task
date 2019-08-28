package tests;

import Base.SqlBaseTest;
import PageBlocks.SqlPageTableRow;
import Pages.SqlPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static Enums.CustomerEntityFields.Address;
import static Enums.CustomerEntityFields.ContactName;

public class T2_CheckNumberOfLondonEntries extends SqlBaseTest {
    private static final String QUERY = "SELECT * FROM Customers WHERE city='London';";

    @Test(description = "Check the number of entries for London city.")
    public void test() {
        List<SqlPageTableRow> rowsWithCondition =
                (new SqlPage())
                .openPage()
                .setSqlQuery(QUERY)
                .clickRunSqlButton()
                .getResultsTable()
                .getTableRows();
        Assert.assertEquals(rowsWithCondition.size(), 6, "There should be exactly 6 entries with London city.");
    }
}
