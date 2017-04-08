package basicweb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverMac {

	public static void main(String[] args) {
		// http://chromedriver.storage.googleapis.com/index.html
		String baseURL = "http://www.google.com";
		String[] baseArrayURL = {"http://www.google.com", "http://www.yahoo.com", "http://www.demoJava.com"};
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "/Users/dpatel05/Documents/workspace_personal/selenium/chromedriver");
		
		driver = new ChromeDriver();
		
		driver.get(baseURL);

	}

}


// for Maven Download
// http://download.eclipse.org/technology/m2e/releases/

// eclipse -> help -> install new Software
// also you will need to restart the eclipse

// to install selenium 
// visit http://www.seleniumhq.org/download/

// We need this Selenium Client & WebDriver Language Bindings
