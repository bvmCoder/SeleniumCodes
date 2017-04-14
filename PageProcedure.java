import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PageProcedure {
	private WebDriver driver;
	private String baseUrl;
	public static WebElement element = null; // intentially null so later I can use this Variable

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\pateldi\\Documents\\SeleniumBindings\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "https://www.expedia.com/";

		// Maximize the browser's window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public static WebElement flightsTabElement(WebDriver webDriver, String idName) {
		element = webDriver.findElement(By.id(idName));
		return element;
	}
	
	public static WebElement originTextBoxElement(WebDriver webDriver, String idName) {
		element = webDriver.findElement(By.id(idName));
		return element;
	}
	
	public static WebElement destinationTextBoxElement(WebDriver webDriver, String idName) {
		element = webDriver.findElement(By.id(idName));
		return element;
	}

	public static WebElement departureDateTextBoxElement(WebDriver webDriver, String idName) {
		element = webDriver.findElement(By.id(idName));
		return element;
	}
	
	public static WebElement returnDateTextBoxElement(WebDriver webDriver, String idName) {
		element = webDriver.findElement(By.id(idName));
		return element;
	}
	
	public static void FillOriginTextBox(WebDriver webDriver, String idName, String fillValue) {
		element = originTextBoxElement(webDriver, idName);
		ll
	}

	@Test
	public void test() throws Exception {
		driver.get(baseUrl);
		// Navigate to the Flights Tab Only
		WebElement flightsTab = flightsTabElement(driver, "tab-flight-tab-hp");
		// WebElement flightsTab =	driver.findElement(By.id("tab-flight-tab-hp"));
		flightsTab.click();
		Thread.sleep(1000);
		
		

		// Target Origin Text Box and add Origin City to it
		// WebElement originTextBox = originTextBoxElement(driver, "flight-origin-hp-flight");
		// WebElement originTextBox = driver.findElement(By.id("flight-origin-hp-flight"));
		FillOriginTextBox(driver, "tab-flight-tab-hp", "New York");
		Thread.sleep(1000);

		// Target Destination Text Box and add Destination City to it
		WebElement destinationTextBox = destinationTextBoxElement(driver, "flight-destination-hp-flight");
		// WebElement destinationTextBox = driver.findElement(By.id("flight-destination-hp-flight"));
		destinationTextBox.sendKeys("Florida");
		Thread.sleep(1000);

		WebElement departureDateTextBox = departureDateTextBoxElement(driver, "flight-departing-hp-flight");
		// WebElement departureDateTextBox = driver.findElement(By.id("flight-departing-hp-flight"));
		departureDateTextBox.sendKeys("04/26/2017");
		Thread.sleep(1000);

		WebElement returnDateTextBox = returnDateTextBoxElement(driver, "flight-returning-hp-flight");
		// WebElement returnDateTextBox = driver.findElement(By.id("flight-returning-hp-flight"));
		returnDateTextBox.sendKeys("04/30/2017");
		Thread.sleep(1000);

	}

	@After
	public void tearDown() throws Exception {
		Thread.sleep(2000);
		//driver.quit();
	}

}
