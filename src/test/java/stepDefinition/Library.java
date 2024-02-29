package stepDefinition;

import java.time.Duration;
import java.util.Iterator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Library {

	public void waitForVisibilityOfElement(WebDriver driver,By visbleElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(visbleElement));
	}
	
	public void waitForVisibilityOfElement(WebDriver driver,By visbleElement,int sec) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
		wait.until(ExpectedConditions.visibilityOfElementLocated(visbleElement));
	}

	public void clickOn(WebDriver driver,By element) {
		waitForVisibilityOfElement(driver,element);
		driver.findElement(element).click();
	}
	
	public void clickJSEon(WebDriver driver,By element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(element));
        
	}
	
	public void scrollToElement(WebDriver driver,By element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(element));
    }
	
	public void switchToNewWindow(WebDriver driver) {
		String currentWindow = driver.getWindowHandle();
		Iterator<String> windows = driver.getWindowHandles().iterator();
		while (windows.hasNext()) {
			String window = windows.next();
			if (!currentWindow.equalsIgnoreCase(window)) {
				driver.switchTo().window(window);
				driver.manage().window().maximize();
				break;
			}
		}
	}
}
