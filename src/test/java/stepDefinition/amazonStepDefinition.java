package stepDefinition;

import static org.testng.Assert.fail;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class amazonStepDefinition {

	public WebDriver driver;
	private Scenario myScenario;
	private static final Logger lOGGER = LoggerFactory.getLogger(amazonStepDefinition.class);
	PageElements elements = new PageElements();
	Library lib = new Library();
	String userDir = System.getProperty("user.dir");
	String screenshotPath = userDir + "\\ScreenShots";

	@Given("Launch the url {string}")
	public void launchTheUrl(String url) {
		driver.get(url);
	}

	@When("Click on {string} in the menu")
	public void clickOnProductInTheMenu(String menu) throws Exception {
		By product = By.xpath("//div[@id='text']//li//a//div[contains(text(),\"" + menu + "\")]");
		if (driver.findElements(product).size() == 0) {
			fail("Menu button not clicked");
		}
		Thread.sleep(2000);
		lib.clickJSEon(driver, product);
		By element = By.xpath("//div[@id='hmenu-content']//ul//li//a[contains(text(),\"" + menu + "\")]");
		try {
			lib.waitForVisibilityOfElement(driver, element, 10);
		} catch (Exception e) {
			lib.clickOn(driver, elements.menuBtn);
			lib.clickJSEon(driver, product);
		}
		lib.clickJSEon(driver, element);
	}

	@When("click on menu button")
	public void clickOnMenuButton() throws Exception {
		lib.clickOn(driver, elements.menuBtn);
	}

	@When("Filter by star as {string}")
	public void filterByStar(String star) throws Exception {
		By review = By.xpath(
				"//span[text()=\"Avg. Customer Review\"]/parent::div/following-sibling::ul//div//span[contains(text(),'"
						+ star + "')]/parent::i");
		lib.scrollToElement(driver, review);
		lib.clickOn(driver, review);
		captureScreenShot();
	}

	@When("Filter by price as {string}")
	public void filterByPrice(String amount) throws Exception {
		By review = By.xpath(
				"//span[text()=\"Price\"]/parent::div/following-sibling::ul//span//li//a//span[contains(text(),\""
						+ amount + "\")]");
		lib.scrollToElement(driver, review);
		lib.clickOn(driver, review);
	}

	@When("Select the brand {string} and {string}")
	public void selectTheBrandAnd(String product1, String product2) throws Exception {
		By brand1 = By.xpath("//span[text()=\"Brand\"]/parent::div/following-sibling::ul//li[@aria-label='" + product1
				+ "']//input");
		By brand2 = By.xpath("//span[text()=\"Brand\"]/parent::div/following-sibling::ul//li[@aria-label='" + product2
				+ "']//input");
		Thread.sleep(2000);
		lib.scrollToElement(driver, brand1);
		lib.clickJSEon(driver, brand1);
		Thread.sleep(2000);
		lib.clickJSEon(driver, brand2);
	}

	@When("Count number of result")
	public void countNumberOfResult() throws Exception {
		lib.waitForVisibilityOfElement(driver, elements.imagecount);
		int imageCount = driver.findElements(elements.imagecount).size();
		System.out.println("Total no.of product shown is: "+imageCount);
		captureScreenShot();
	}

	@When("Select the {string} product")
	public void selectTheProduct(String count) {
		By product = By.xpath("(//div[@class='s-image-padding'])[" + count + "]");
		lib.clickOn(driver, product);
	}

	@When("Check the item is added to the cart")
	public void checkTheItemIsAddedToTheCart() throws Exception {
		lib.switchToNewWindow(driver);
		lib.clickOn(driver, elements.addToCartBtn);
		lib.waitForVisibilityOfElement(driver, elements.cartCount);
		String cartValue = driver.findElement(elements.cartCount).getText();
		System.out.println("Total no.of product added to the cart is: "+cartValue);
		if (!cartValue.equalsIgnoreCase("1")) {
			fail("Item not added to the cart");
		}
		captureScreenShot();
	}

	@Before
	public void launchBrowser(Scenario scenario) throws Exception {
		myScenario = scenario;
		myScenario.getId();

		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	@After
	public void tearDown(Scenario scenario) throws Exception {
		if (scenario.isFailed()) {
			try {
				captureScreenShot();
			} catch (Exception e) {
			}
		}
		driver.close();
		driver.quit();

	}

	public void takeScreenShyot(String ssName) throws Exception {
		try {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			myScenario.attach(screenshot, "image/png", ssName);
		} catch (WebDriverException somePlatformsDontSupportScreenshots) {
			lOGGER.error("An error occurred while taking a screenshot:", somePlatformsDontSupportScreenshots);
		} catch (ClassCastException cce) {
		}
	}

	public void captureScreenShot() {
		try {
			Robot robot = new Robot();
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenCapture = robot.createScreenCapture(screenRect);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			String formattedDateTime = now.format(formatter);
			String filePath = "" + screenshotPath + "/" + formattedDateTime + ".png";
			File screenshotFile = new File(filePath);
			ImageIO.write(screenCapture, "png", screenshotFile);

			System.out.println("Screenshot saved to: " + screenshotFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
