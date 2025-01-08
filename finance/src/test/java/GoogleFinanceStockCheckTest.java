import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageobjects.GoogleFinancePage;

import static org.testng.Assert.assertTrue;

public class GoogleFinanceStockCheckTest {
    private WebDriver driver;

    @BeforeMethod
    public void initializeSelenium() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void checkInterestedStockSymbols() {
        GoogleFinancePage googleFinance = new GoogleFinancePage(driver);
        googleFinance.goTo();
        assertTrue(googleFinance.isPageTitleCorrect());

        List<String> interestedStockSymbols = googleFinance.getInterestedStockSymbols();

        for (String s : interestedStockSymbols) {
            System.out.println(s);
        }
    }
}
