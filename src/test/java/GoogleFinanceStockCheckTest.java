import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageobjects.GoogleFinancePage;

import static org.testng.Assert.assertTrue;

/**
 * This class checks stocks on the Google Finance page.
 */
public class GoogleFinanceStockCheckTest {
    private WebDriver driver;

    /**
     * Sets up Chrome driver in headless and a default window size to run in CI.
     */
    @BeforeMethod
    public void initializeSelenium() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    /**
     * Quits the Chrome browser to ensure it doesn't remain around after test
     * execution.
     */
    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    /**
     * Extracts the expected stock symbols from a JSON file
     * 
     * @return a List of strings of expected stock symbols from the JSON file
     */
    public List<String> getExpectedStockSymbols() {
        List<String> expectedStockSymbols = null;
        ClassLoader classLoader = GoogleFinanceStockCheckTest.class.getClassLoader();
        File stockSymbolTestData = new File(classLoader.getResource("stockSymbols.json").getFile());

        try {
            FileInputStream fis = new FileInputStream(stockSymbolTestData);
            JSONTokener tokener = new JSONTokener(fis);
            JSONObject stockSymbolsJSON = new JSONObject(tokener);
            JSONArray stocksArray = stockSymbolsJSON.getJSONArray("stocks");

            expectedStockSymbols = new ArrayList<String>();
            for (int i = 0; i < stocksArray.length(); i++) {
                expectedStockSymbols.add(stocksArray.getString(i));
            }
        } catch (FileNotFoundException exec) {
            throw new RuntimeException("Failed to load stockSymbols.json", exec);
        }

        return expectedStockSymbols;
    }

    /**
     * Compares and prints interested vs. expected stock symbols on Google Finance
     */
    @Test
    public void checkInterestedStockSymbols() {
        // Precondition: Extracts test data from a JSON file
        List<String> expectedStockSymbols = getExpectedStockSymbols();

        // 1. Load www.google.com/finance
        GoogleFinancePage googleFinance = new GoogleFinancePage(driver);
        googleFinance.goTo();

        // 2. Verify the page is loaded by asserting the page title
        assertTrue(googleFinance.isPageTitleCorrect());

        // 3. Retrieves the stock symbols listed under "You may be interested in"
        List<String> interestedStockSymbols = googleFinance.getInterestedStockSymbols();

        // 4. Compare stock symbols from interested vs. expected stock symbols
        List<String> interestedOnlyStockSymbols = interestedStockSymbols.stream()
                .filter(stockSymbol -> !expectedStockSymbols.contains(stockSymbol))
                .collect(Collectors.toList());
        List<String> expectedOnlyStockSymbols = expectedStockSymbols.stream()
                .filter(stockSymbol -> !interestedStockSymbols
                        .contains(stockSymbol))
                .collect(Collectors.toList());

        // 5. Print stock symbols that are interested, but not expected stock symbols
        System.out.println("Interested only stock symbols: " + interestedOnlyStockSymbols.toString());

        // 6. Print stock symbols that are expected, but not interested stock symbols
        System.out.println("Expected only stock symbols: " + expectedOnlyStockSymbols.toString());
    }
}
