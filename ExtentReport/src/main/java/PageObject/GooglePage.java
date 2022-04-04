package PageObject;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GooglePage extends BasePage {

    private String url;

    private WebDriver driver;

    //@FindBy(id = "lst-ib")
    @FindBy(name = "q")
    private WebElement searchTextBox;

    @FindBy(how = How.NAME, using = "q")
    WebElement searchBox;

    @FindBy(how = How.NAME, using = "btnK")
    WebElement submit;

    @FindBy(name = "btnK")
    private WebElement googleSearchBtn;

    @FindBy(id = "resultStats")
    private WebElement resultCount;

    @FindBys(@FindBy(css = "div .sbqs_c"))
    private List<WebElement> suggestions;

    @FindBys(@FindBy(xpath = "//ul[@class='G43f7e']/li"))
    private List<WebElement> listxpath;


    @FindBy(name= "q")
    private WebElement searchboxSuggestion;

    private String searchQuery = "";

    public String getSearchQuery() {
        return searchQuery;
    }

    private void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public GooglePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.url = "https://www.google.com";
    }


    @Override
    public boolean isAt() {
        return false;
    }

    public void goTo() {
        this.driver.get(this.url);
    }

    public void enterSearchQuery(String text) {
        setSearchQuery(text);
        WebDriverWait wait = new WebDriverWait(this.driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(searchBox)).clear();
        searchBox.sendKeys(getSearchQuery());

    }

    public void clickGoogleSearchBtn() {
        submit.click();
    }

    public boolean verifySuggestionExist(String suggestionText, Integer arg1) throws InterruptedException {

        Wait wait = new FluentWait(this.driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

        List<WebElement> listpath = listxpath;
       // System.out.println("list_size" + listpath.size());
        for(WebElement ele: listpath){
            System.out.println(ele.getText());
        }

        for(int i = 0;i<listpath.size();i++){
            if(listpath.get(arg1).getText().contains(suggestionText)){
                return true;
            }else
            {
                return true;
            }
        }
        return false;

    }
}
