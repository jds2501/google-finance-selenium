package pageobjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleFinancePage {
    private final String url = "https://www.google.com/finance/";
    private final String pageTitle = "Google Finance";
    private final By interestedStockSymbols = By
            .cssSelector("section[aria-labelledby='smart-watchlist-title'] div[class='COaKTb']");
    private final Duration defaultWait = Duration.ofSeconds(10);
    private WebDriver driver;

    public GoogleFinancePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        this.driver.get(url);
    }

    public boolean isPageTitleCorrect() {
        return (new WebDriverWait(this.driver, defaultWait)).until(
                ExpectedConditions.titleContains(pageTitle));
    }

    public List<String> getInterestedStockSymbols() {
        List<WebElement> interestedStockSymbolElements = (new WebDriverWait(driver, defaultWait)).until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(this.interestedStockSymbols));

        ArrayList<String> interestedStockSymbolsText = new ArrayList<String>();
        for (WebElement interestedStockSymbolElement : interestedStockSymbolElements) {
            interestedStockSymbolsText.add(interestedStockSymbolElement.getText());
        }

        return interestedStockSymbolsText;
    }
}
