package test.java.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {
    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filePath = "./screenshots/" + screenshotName + ".png";
            FileUtils.copyFile(srcFile, new File(filePath));
            System.out.println("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
