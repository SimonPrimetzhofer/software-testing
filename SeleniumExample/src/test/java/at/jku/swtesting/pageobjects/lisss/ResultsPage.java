package at.jku.swtesting.pageobjects.lisss;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.util.Random;

public class ResultsPage {

    protected WebDriver driver;

    public ResultsPage(WebDriver driver) {
        this.driver = driver;

        // wait till the result page is loaded
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> d.getTitle().startsWith("JKU | LISSS - "));

        // resize page to get hidden object to appear
        boolean displayed = false;
        do {
            try {
                displayed = driver.findElement(By.xpath("//span[contains(.,'1-10 of ')]")).isDisplayed();
            } catch(NoSuchElementException e) {
                driver.manage().window().setSize(new Dimension(400, 900));
                driver.manage().window().setSize(new Dimension(1100, 900));
            }
        } while(!displayed);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public int getNumberOfResults() {
        try {
            return NumberFormat.getNumberInstance(java.util.Locale.US).parse(driver.findElement(By.xpath("//span[contains(.,'1-10 of ')]")).getText().split(" ")[2]).intValue();
        } catch(ParseException e) {
            return -1;
        }
    }

    public int getPageNumber() {
        return Integer.parseInt(driver.findElement(By.xpath("//span[contains(.,'Page ')]")).getText().split(" ")[1]);
    }

    public String getSearchBarContent() {
        return driver.findElement(By.id("searchBar")).getAttribute("value");
    }

}