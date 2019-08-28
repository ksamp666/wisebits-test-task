package tests;

import Base.SqlBaseTest;
import PageBlocks.SqlPageTableRow;
import Pages.SqlPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static Enums.CustomerEntityFields.Address;
import static Enums.CustomerEntityFields.ContactName;

public class T1_CheckTableRows extends SqlBaseTest {
    private final static String CONTACT_NAME_TO_SEARCH_FOR = "Giovanni Rovelli"; // В ТЗ указано "СGiovanni Rovelli", но мне показалось что это опечатка и я заменил на "Giovanni Rovelli"
    private final static String CONTACT_ADDRESS_TO_CHECK = "Via Ludovico il Moro 22";

    @Test(description = "Show all table rows and check that ‘СGiovanni Rovelli’ rows has address fields as ‘Via Ludovico il Moro 22’")
    public void test() {
        List<SqlPageTableRow> rowsWithCondition =
                (new SqlPage())
                        .openPage()
                        .clickRunSqlButton()
                        .getResultsTable()
                        .getRowsByColumnValue(ContactName.toString(), CONTACT_NAME_TO_SEARCH_FOR);
        Assert.assertEquals(rowsWithCondition.size(), 1, "There can be only one " + CONTACT_NAME_TO_SEARCH_FOR + " in table.");
        Assert.assertEquals(rowsWithCondition.get(0).getValueForColumn(Address.toString()), CONTACT_ADDRESS_TO_CHECK, CONTACT_NAME_TO_SEARCH_FOR + "address should be " + CONTACT_ADDRESS_TO_CHECK);
    }
}
