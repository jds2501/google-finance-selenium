package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

/**
 * This class is a utility class that enables capturing screenshots on failures
 */
public class ScreenshotUtil {

    /**
     * Captures a screenshot using the provided WebDriver and screenshot file name.
     * 
     * @param driver         the Selenium web driver
     * @param screenshotName the name of the file to use for the screenshot
     */
    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotsDir = new File("./screenshots");
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }
            String filePath = "./screenshots/" + screenshotName + ".png";
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
