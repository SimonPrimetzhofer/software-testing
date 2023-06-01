package at.jku.swtesting.pageobjects.lisss;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SimpleSearchPage {

    protected WebDriver driver;

    @FindBy(id = "searchBar")
    WebElement searchField;

    public SimpleSearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ResultsPage searchFor(String searchText) {
        searchField.clear();
        searchField.sendKeys(searchText);
        searchField.submit();

        return new ResultsPage(driver);
    }

}