import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ListOfElementTest {
	WebDriver driver; // Data Type is Webdriver and variable is driver
	String baseUrl;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\pateldi\\Documents\\SeleniumBindings\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "http://www.html.am/html-codes/forms/html-radio-button-code.cfm";
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		System.out.println("Excecuted Before The Test Run...");
	}

	@Test
	public void test() throws Exception {
		driver.get(baseUrl);
		List<WebElement> radioButtons = driver.findElements(By.xpath(".//input[@name='gender']"));
		// WebElement radioButtons = driver.findElement(By.name("gender"));
		int size = radioButtons.size();
		System.out.println("The Size of the List is: " + size);

	}

	@After
	public void tearDown() throws Exception {
		Thread.sleep(3000);
		driver.quit();
	}

}
