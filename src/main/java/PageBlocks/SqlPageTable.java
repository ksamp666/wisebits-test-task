package PageBlocks;

import com.wiley.elements.SearchStrategy;
import com.wiley.elements.TeasyElement;
import com.wiley.elements.types.TeasyElementList;
import com.wiley.page.AbstractBlock;
import io.qameta.allure.Step;
import junit.framework.Assert;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SqlPageTable extends AbstractBlock {

    private final static String HEADER_ELEMENTS = ".//tr/th";
    private final static String ROW_LOCATOR = ".//tr/td/ancestor::tr";

    private List<SqlPageTableRow> tableRows = new ArrayList<>();

    public SqlPageTable(TeasyElement element) {
        super(element);
        Map<String, Integer> columnsMap = getColumnsMap();
        fillTableRowsList(columnsMap);
    }

    private Map<String, Integer> getColumnsMap() {
        Map<String, Integer> columnsMap = new HashMap<>();
        TeasyElementList headerElements = getMainElement().elements(By.xpath(HEADER_ELEMENTS));
        headerElements.forEach(element -> {
            columnsMap.put(element.getText(), headerElements.indexOf(element));
        });
        return columnsMap;
    }

    private void fillTableRowsList(Map<String, Integer> columnsMap) {
        TeasyElementList rows = getMainElement().elements(By.xpath(ROW_LOCATOR));
        rows.forEach(row -> {
            tableRows.add(new SqlPageTableRow(row, columnsMap));
        });
    }

    @Step("Get table rows by column value")
    public List<SqlPageTableRow> getRowsByColumnValue(String column, String value) {
        return tableRows.stream().filter(row -> row.getValueForColumn(column).equals(value)).collect(Collectors.toList());
    }

    @Step("Get all table rows")
    public List<SqlPageTableRow> getTableRows() {
        return tableRows;
    }
}
