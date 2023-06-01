package at.jku.swtesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}

    @AfterAll
    public static void tearDown() {
		driver.close();
		driver.quit();
	}

	@Test
	public void testSearch() throws ParseException {

		driver.get("https://lisss.jku.at/primo-explore/search?vid=ULI&lang=en_US");

		WebElement confirmButton = driver.findElement(By.cssSelector("button.button-confirm"));
		confirmButton.click();

		WebElement searchField = driver.findElement(By.id("searchBar"));
		searchField.clear();
		searchField.sendKeys("software testing");
		searchField.submit();

		int resultsFound = NumberFormat.getNumberInstance(java.util.Locale.US).parse(driver.findElement(By.xpath("//span[contains(.,'1-10 of ')]")).getText().split(" ")[2]).intValue();
		assertEquals(4253, resultsFound);
		assertEquals("JKU | LISSS - software testing", driver.getTitle());
	}


}
