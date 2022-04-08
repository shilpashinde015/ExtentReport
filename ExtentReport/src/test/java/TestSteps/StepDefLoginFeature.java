package TestSteps;
import PageObject.loginFeature;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static TestRunner.testRunner.test1;

public class StepDefLoginFeature {
   WebDriver driver;
   loginFeature logfea;
   String path;
   String sheetName;

    @Before
   public void setUp() throws IOException {
       System.setProperty("webdriver.chrome.driver",
               "/home/shilpa/Downloads/chromedriver");
       path = "/home/shilpa/IdeaProjects/ExtentReport/src/test/resources/testdata.xlsx";
       sheetName = "details";

       driver = new ChromeDriver();

       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
       logfea = new loginFeature(driver);
       test1.log(Status.INFO, "Starting test case");
   }

    @Given("^User is on Home Page$")
    public void user_is_on_Home_Page() throws Throwable {
        logfea.goToHome();
        test1.pass("Navigate to demoQA.com");
    }
   @When("^User Navigate to LogIn Page$")
    public void user_Navigate_to_LogIn_Page() throws Throwable {
       // driver.findElement(By.xpath(".//*[@id='account']/a")).click();
        logfea.loginAccount();
    }

    @When("^User enters username and password$")
    public void user_enters_UserName_and_Password() throws Throwable {
        String username;
        String password;
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb=new XSSFWorkbook(inputStream);
        XSSFSheet sheet=wb.getSheet(sheetName);

        int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
        for(int i=1;i<rowCount;i++) {
            username = sheet.getRow(i).getCell(0).getStringCellValue();
            password = sheet.getRow(i).getCell(1).getStringCellValue();
            System.out.println("username : " + username +"password: " + password);
            logfea.enterCredential(username, password);

        }
    }
  @And("^Message displayed Login Successfully$")
    public void message_displayed_Login_Successfully() throws Throwable {
        logfea.displayMessage();
    }
    @Then("write to the file")
    public void write_to_the_file() throws Throwable{

        Map<String, Object[]> map = logfea.Read_file(path);
        logfea.write_file(map,"pass");
    }
    @After
    public void teardown() {

        driver.quit();
    }


}
