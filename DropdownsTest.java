import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownsTest {

	private WebDriver driver;
	String baseUrl;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\pateldi\\Documents\\SeleniumBindings\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "https://www.expedia.com/";
		// Maximize the browser's window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		System.out.println("Excecuted Before The Test Run...");
	}

	@Test
	public void test() throws Exception {
		driver.get(baseUrl);
		
		Select sel1 = new Select(driver.findElement(By.id("package-1-adults-hp-package")));
		Select sel2 = new Select(driver.findElement(By.id("package-1-children-hp-package")));
		System.out.println("Here is my Adult Dropdown Select: " + sel1);
		System.out.println("Here is my Children Dropdown Select: " + sel2);
		List<WebElement> sel1Options = sel1.getOptions();
		int size = sel1Options.size();
		System.out.println("The Size of the Select Options List is: " + size);
		for(int i = 0; i < size; i++) {
			String optionName = sel1Options.get(i).getText();
			System.out.println(optionName);
		}
		
		// By Value
		sel1.selectByValue("4");
		Thread.sleep(3000);
		// By Index
		sel1.selectByIndex(1);		
	}

	@After
	public void tearDown() throws Exception {
		Thread.sleep(3000);
		driver.quit();
	}


}
