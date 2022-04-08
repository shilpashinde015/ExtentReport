package TestSteps;

import PageObject.GooglePage;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static TestRunner.testRunner.test1;

public class StepDefinition {
    WebDriver driver;
    GooglePage googleUrl;


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "/home/shilpa/Downloads/chromedriver");

        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        googleUrl = new GooglePage(driver);
        test1.log(Status.INFO, "Starting test case");
    }


    @Given("^I go to google$")
    public void iGoToGoogle() throws Throwable {
        googleUrl.goTo();
        test1.pass("Navigate to google.com");

    }

    @When("^I query for \"([^\"]*)\"$")
    public void iQueryFor(String query) throws Throwable {
        googleUrl.enterSearchQuery(query);
    }

    @And("^click search$")
    public void clickSearch() throws Throwable {
        googleUrl.clickGoogleSearchBtn();
        test1.pass("Searchbutton working Properly !");
    }

    @Then("^google page title should become \"([^\"]*)\"$")
    public void googlePageTitleShouldBecome(String pageTitle) throws Throwable {
        System.out.println(googleUrl.getSearchQuery());
        System.out.println(pageTitle);
        test1.pass("Page title is correct");
        Assert.assertEquals(googleUrl.getSearchQuery() +" - Google Search", pageTitle);
    }

    @Then("^i should see \"([^\"]*)\" as (\\d+) of the suggested search$")
    public void iShouldSeeAsOfTheSuggestedSearchString (String suggestion, Integer arg1) throws Throwable {

        Assert.assertTrue(googleUrl.verifySuggestionExist(suggestion,arg1));
        test1.pass("Suggestion box working properly !");

    }

    @After
    public void teardown() {

        driver.quit();
    }

}
