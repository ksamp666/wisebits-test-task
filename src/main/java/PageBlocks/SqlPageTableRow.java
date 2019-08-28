package PageBlocks;

import com.wiley.elements.TeasyElement;
import com.wiley.page.AbstractBlock;
import org.openqa.selenium.By;

import java.util.Map;


public class SqlPageTableRow extends AbstractBlock {
    private Map<String, Integer> columnsMap;
    private final static String ROW_LOCATOR = ".//td[%s]";

    public SqlPageTableRow(TeasyElement element, Map<String, Integer> columnsMap) {
        super(element);
        this.columnsMap = columnsMap;
    }

    public String getValueForColumn(String column) {
        return getMainElement().element(By.xpath(String.format(ROW_LOCATOR, columnsMap.get(column)+1))).getText();
    }
}
