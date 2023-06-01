package at.jku.swtesting;

import at.jku.swtesting.pageobjects.google.ResultsPage;
import at.jku.swtesting.pageobjects.google.SearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LisssPageObjectTest {

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
	public void testSearch() {
		driver.get("https://www.google.at/");
		SearchPage searchPage = new SearchPage(driver);
		searchPage.confirmTerms();

			ResultsPage resultsPage = searchPage.searchFor("testing");
				
		assertEquals("testing - Google Suche", resultsPage.getTitle());
	}


}
