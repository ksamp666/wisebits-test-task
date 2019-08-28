package Pages;

import PageBlocks.SqlPageTable;
import com.wiley.holders.DriverHolder;
import com.wiley.page.BasePage;
import com.wiley.page.PageProvider;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class SqlPage extends BasePage {
    private final static String PAGE_URL = "https://www.w3schools.com/sql/trysql.asp?filename=trysql_select_all";

    private final static By RUN_SQL_BUTTON = By.xpath("//button[text()='Run SQL »']");
    private final static By RESULTS_PAGE = By.cssSelector("#resultSQL table");
    private final static By SQL_QUERY_FIELD = By.cssSelector("#textareaCodeSQL");

    @Step("Open SQL page in browser")
    public SqlPage openPage() {
        return PageProvider.get(SqlPage.class, PAGE_URL);
    }

    @Step("Set SQL query")
    public SqlPage setSqlQuery(String query) {
        ((JavascriptExecutor) DriverHolder.getDriver()).executeScript("window.editor.setValue('" + query.replace("'", "\\'").replace("\"", "\\\"") + "')");
        return this;
    }

    @Step("Click 'Run SQL' button.")
    public SqlPage clickRunSqlButton() {
        element(RUN_SQL_BUTTON).click();
        return this;
    }

    @Step("Get results table")
    public SqlPageTable getResultsTable() {
        return (new SqlPageTable(element(RESULTS_PAGE)));
    }
}
