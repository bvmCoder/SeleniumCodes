import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchElement {
	public static WebElement element = null;
	
	public static WebElement universalElement(WebDriver webDriver, String idName) {
		element = webDriver.findElement(By.id(idName));
		return element;
	}
	
	public static void FillUniversalElement(WebDriver webDriver, String idName, String fillValue) {
		element = universalElement(webDriver, idName);
		element.clear();
		element.sendKeys(fillValue);
	}
	
	
	public static WebElement searchButtonElement(WebDriver webDriver, String pathName) {
		element = webDriver.findElement(By.xpath(pathName));
		return element;
	}
	public static void clickOnSearchButtonElement(WebDriver webDriver, String pathName) {
		element = searchButtonElement(webDriver, pathName);
		element.click();
	}
}
