package at.jku.swtesting.pageobjects.lisss;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
	
	protected WebDriver driver;

	@FindBy(id = "searchBar") WebElement searchField;
	@FindBy(css = "button.button-confirm") WebElement confirmButton;
	
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public ResultsPage searchFor(String searchText) {
		searchField.clear();
		searchField.sendKeys(searchText);
		searchField.submit();
		return new ResultsPage(driver);
	}
	
	public void confirmTerms() {
		confirmButton.click();
	}
	
}