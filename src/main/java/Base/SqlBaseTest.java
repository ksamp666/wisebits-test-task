package Base;

import com.wiley.basetests.SeleniumBaseTest;
import com.wiley.driver.WebDriverFactory;
import com.wiley.holders.DriverHolder;
import org.testng.annotations.BeforeClass;

public class SqlBaseTest extends SeleniumBaseTest {
    @BeforeClass(alwaysRun = true)
    public void prepareWebDriver() {
        WebDriverFactory.initDriver();
        DriverHolder.getDriver().manage().window().maximize();
    }
}
