package at.jku.swtesting;

import at.jku.swtesting.pageobjects.lisss.ResultsPage;
import at.jku.swtesting.pageobjects.lisss.SimpleSearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
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
		
		driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
		driver.manage().window().setSize(new Dimension(1100, 900));
	}

    @AfterAll
    public static void tearDown() {
		driver.close();
		driver.quit();
	}

	@Test
	public void testSearch() {
		driver.get("https://lisss.jku.at/primo-explore/search?vid=ULI&lang=en_US");
		SimpleSearchPage searchPage = new SimpleSearchPage(driver);

		ResultsPage resultsPage = searchPage.searchFor("software testing");
				
		assertEquals("JKU | LISSS - software testing", resultsPage.getTitle());

		assertEquals(4253, resultsPage.getNumberOfResults());
	}

	@Test
	public void testAdditionalSearch() {
		driver.get("https://lisss.jku.at/primo-explore/search?vid=ULI&lang=en_US");
		SimpleSearchPage searchPage = new SimpleSearchPage(driver);

		ResultsPage resultsPage = searchPage.searchFor("software testing");

		assertEquals("JKU | LISSS - software testing", resultsPage.getTitle());

		assertEquals(4253, resultsPage.getNumberOfResults());

		assertEquals(1, resultsPage.getPageNumber());

		assertEquals("software testing", resultsPage.getSearchBarContent());
	}


}
