package gr.perisnik.sel.Selenium_Web_Driver;

import java.awt.desktop.SystemSleepEvent;
import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

//import org.openqa.selenium.firefox.FirefoxOptions;

public class My_Selenium {
	
	public static void main(String[] args) throws InterruptedException {
		
		// Initialize ExtentReports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("TestReport.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        ExtentTest test = extent.createTest("TutorialsNinja Test", "Automated Test for Searching and Adding Products");
		
//		FirefoxOptions options = new FirefoxOptions();
//		options.addArguments("--remote-allow-origins=*");
		
		System.setProperty("webdriver.firefox.driver", "C:/Users/pirat/Documents/Projects/WebDrivers/geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
				
		// Create WebDriverWait with a timeout of 15 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // Navigate to the website
            String url = "https://tutorialsninja.com/demo/index.php?route=common/home";
            test.info("Navigating to URL: " + url);
            driver.get(url);

            // Maximize the browser window
            test.info("Maximizing the browser window.");
            driver.manage().window().maximize();
            test.pass("Browser maximized.", attachScreenshot(driver));

            // Wait for the search box to be clickable and click it
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("search")));
            searchBox.click();
            test.pass("Clicked on search box.", attachScreenshot(driver));

            // Enter the term "MacBook" in the search box
            searchBox.sendKeys("MacBook");
            test.pass("Entered 'MacBook' into the search box.", attachScreenshot(driver));

            // Wait for the search button to be clickable and click it
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("input-group-btn")));
            searchButton.click();
            test.pass("Clicked on search button.", attachScreenshot(driver));

            // Wait for the "MacBook Air" product to appear and click it
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='MacBook Air']")));
            productLink.click();
            test.pass("Selected 'MacBook Air' product.", attachScreenshot(driver));

            // Wait for the quantity box to be visible, clear it, and enter "2"
            WebElement quantityBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-quantity")));
            quantityBox.clear();
            quantityBox.sendKeys("2");
            test.pass("Updated quantity to 2.", attachScreenshot(driver));

            // Wait for the "Add to Cart" button to be clickable and click it
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart")));
            addToCartButton.click();
            test.pass("Clicked 'Add to Cart' button.", attachScreenshot(driver));

            // Wait for the success message to appear
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
            String actualMessage = successMessage.getText();
            String expectedMessage = "Success: You have added MacBook Air to your shopping cart!";

            // Verify the success message
            if (actualMessage.contains(expectedMessage)) {
                test.pass("Test Passed: The success message is correct.", attachScreenshot(driver));
            } else {
                test.fail("Test Failed: The success message is incorrect.", attachScreenshot(driver));
            }
        } catch (Exception e) {
            // Log the error in the report with a screenshot
            test.fail("An error occurred: " + e.getMessage(), attachScreenshot(driver));
            e.printStackTrace();
        } finally {
            // Close the browser and generate the report
            driver.quit();
            extent.flush();
        }
    }

    // Utility method to take a screenshot and return it as a MediaEntityModelProvider
	private static Media attachScreenshot(WebDriver driver) {
	    try {
	        // Creates folder screenshots if it doesn't exists
	        String folderPath = System.getProperty("user.dir") + "/screenshots";
	        File folder = new File(folderPath);
	        if (!folder.exists()) {
	            folder.mkdir();
	        }

	        // Saves the screenshots
	        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        String screenshotPath = folderPath + "/screenshot_" + System.currentTimeMillis() + ".png";
	        FileUtils.copyFile(srcFile, new File(screenshotPath));

	        // Uses paths
	        String relativePath = "screenshots/" + new File(screenshotPath).getName();
	        return MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build();
	    } catch (Exception e) {
	        System.err.println("Failed to take screenshot: " + e.getMessage());
	        return null;
	    }
	}
}