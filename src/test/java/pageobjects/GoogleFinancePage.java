package pageobjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ScreenshotUtil;

/**
 * This class is a page object representing google.com/finance
 */
public class GoogleFinancePage {
    // URL of the Google Finance page for stock tracking
    private final String url = "https://www.google.com/finance/";

    // Part of the Google Finance page title
    private final String pageTitle = "Google Finance";

    // Selector for stock symbols in "You may be interested in"
    private final By interestedStockSymbols = By
            .cssSelector("section[aria-labelledby='smart-watchlist-title'] div[class='COaKTb']");

    // Default wait time before Selenium times out waiting for an element
    private final Duration defaultWait = Duration.ofSeconds(20);

    // WebDriver instance for browser interactions
    private WebDriver driver;

    /**
     * Creates a Google Finance page object with a Selenium WebDriver
     * 
     * @param driver the Selenium WebDriver
     */
    public GoogleFinancePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Loads the target URL using the Selenium web driver.
     */
    public void goTo() {
        this.driver.get(url);
    }

    /**
     * Checks if the page title contains the Google Finance page title
     * 
     * @return true if the page title contains Google Finance
     */
    public boolean isPageTitleCorrect() {
        return (new WebDriverWait(this.driver, defaultWait)).until(
                ExpectedConditions.titleContains(pageTitle));
    }

    /**
     * Extracts the "You may be interested in" stock symbols and returns it as a
     * List of Strings.
     * 
     * @return a list of Strings containing the stock symbols from "You may be
     *         interested in"
     */
    public List<String> getInterestedStockSymbols() {
        List<WebElement> interestedStockSymbolElements = null;

        try {
            interestedStockSymbolElements = (new WebDriverWait(driver, defaultWait)).until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(this.interestedStockSymbols));
        } catch (Exception exec) {
            ScreenshotUtil.captureScreenshot(driver, "noInterestedStockSymbolElements");
            throw new RuntimeException("Failed to find interested stock symbol elements");
        }

        ArrayList<String> interestedStockSymbolsText = new ArrayList<String>();
        for (WebElement interestedStockSymbolElement : interestedStockSymbolElements) {
            interestedStockSymbolsText.add(interestedStockSymbolElement.getText());
        }

        return interestedStockSymbolsText;
    }
}
