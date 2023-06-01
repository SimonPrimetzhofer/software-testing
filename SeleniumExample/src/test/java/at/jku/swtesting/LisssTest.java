package at.jku.swtesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LisssTest {

	private static WebDriver driver;
	private static final DriverManagerType DRIVER_TYPE = DriverManagerType.CHROME;

	@BeforeAll
	public static void setUp() { 
		WebDriverManager.getInstance(DRIVER_TYPE).setup(); 
		driver = new ChromeDriver();
		// driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
		driver.manage().window().setSize(new Dimension(1100, 900));
	}

    @AfterAll
    public static void tearDown() {
		driver.close();
		driver.quit();
	}

	@Test
	public void testSearch() throws ParseException {

		driver.get("https://lisss.jku.at/primo-explore/search?vid=ULI&lang=en_US");

		WebElement searchField = driver.findElement(By.id("searchBar"));
		searchField.clear();
		searchField.sendKeys("software testing");
		searchField.submit();

		// resize page to get hidden object to appear
		boolean displayed = false;
		do{
			try{
				displayed = driver.findElement(By.xpath("//span[contains(.,'1-10 of ')]")).isDisplayed();
			} catch (NoSuchElementException e){
				driver.manage().window().setSize(new Dimension(400, 900));
				driver.manage().window().setSize(new Dimension(1100, 900));
			}
		} while(!displayed);

		int resultsFound = NumberFormat.getNumberInstance(java.util.Locale.US).parse(driver.findElement(By.xpath("//span[contains(.,'1-10 of ')]")).getText().split(" ")[2]).intValue();
		assertEquals(4253, resultsFound);
		assertEquals("JKU | LISSS - software testing", driver.getTitle());
	}


}
