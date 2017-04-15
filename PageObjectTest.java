import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PageObjectTest {
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
	

	@Test
	public void test() throws Exception {
		driver.get(baseUrl);
		// Navigate to the Flights Tab Only
		SearchElement.universalElement(driver, "tab-flight-tab-hp").click();
		Thread.sleep(1000);

		SearchElement.FillUniversalElement(driver, "tab-flight-tab-hp", "New York");
		Thread.sleep(1000);

		// Target Destination Text Box and add Destination City to it
		SearchElement.FillUniversalElement(driver, "flight-destination-hp-flight", "Florida");
		Thread.sleep(1000);

		SearchElement.FillUniversalElement(driver, "flight-departing-hp-flight", "04/26/2017");
		Thread.sleep(1000);

		SearchElement.FillUniversalElement(driver, "flight-returning-hp-flight", "04/30/2017");		
		Thread.sleep(1000);
		
		SearchElement.clickOnSearchButtonElement(driver, "//*[@id='gcw-flights-form-hp-flight']/div[7]/label/button");
		Thread.sleep(1000);

	}

	@After
	public void tearDown() throws Exception {
		Thread.sleep(2000);
		//driver.quit();
	}
}
