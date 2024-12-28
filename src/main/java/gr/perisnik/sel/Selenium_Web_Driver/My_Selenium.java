package gr.perisnik.sel.Selenium_Web_Driver;

import java.awt.desktop.SystemSleepEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import org.openqa.selenium.firefox.FirefoxOptions;

public class My_Selenium {
	
	public static void main(String[] args) throws InterruptedException {
		
//		FirefoxOptions options = new FirefoxOptions();
//		options.addArguments("--remote-allow-origins=*");
		
		System.setProperty("webdriver.firefox.driver", "C:/Users/pirat/Documents/Projects/WebDrivers/geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
				
		// Create WebDriverWait with a timeout of 15 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // Navigate to the website
            driver.get("https://tutorialsninja.com/demo/index.php?route=common/home");

            // Maximize the browser window
            driver.manage().window().maximize();
            
            // Counts all tags <a></a> and then printed
            int countTagsA = driver.findElements(By.tagName("a")).size();
            System.out.println(countTagsA);

            // Wait for the search box to be clickable and click it
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("search")));
            searchBox.click();

            // Enter the term "MacBook" in the search box
            searchBox.sendKeys("MacBook");

            // Wait for the search button to be clickable and click it
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("input-group-btn")));
            searchButton.click();

            // Wait for the "MacBook Air" product to appear and click it
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='MacBook Air']")));
            productLink.click();

            // Wait for the quantity box to be visible, clear it, and enter "2"
            WebElement quantityBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-quantity")));
            quantityBox.clear();
            quantityBox.sendKeys("2");

            // Wait for the "Add to Cart" button to be clickable and click it
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart")));
            addToCartButton.click();

            // Wait for the success message to appear
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-success")));
            String actualMessage = successMessage.getText();
            String expectedMessage = "Success: You have added MacBook Air to your shopping cart!";

            // Print and verify the success message
            System.out.println("Actual Message: " + actualMessage);
            if (actualMessage.contains(expectedMessage)) {
                System.out.println("Test Passed: The message is correct.");
            } else {
                System.out.println("Test Failed: The message is incorrect.");
            }
        } catch (Exception e) {
            // Print any errors that occur
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}