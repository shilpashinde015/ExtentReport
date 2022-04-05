package TestRunner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


@CucumberOptions(plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        features = "src/test/java/TestSteps/google.feature", glue = "TestSteps")

public class testRunner extends AbstractTestNGCucumberTests {
    public static ExtentSparkReporter htmlReporter;
    public static ExtentReports extent ;
    public static  ExtentTest test1;
    @BeforeClass
    public static void start() {
        htmlReporter = new ExtentSparkReporter("/home/shilpa/IdeaProjects/ExtentReport/Reports/extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test1 = extent.createTest("Google Search Engine", "test to validate Google Search ");
        test1.log(Status.INFO, "Starting test case");


    }
    @AfterClass
    public static void teardown() {
        test1.info("test completed");
        extent.flush();
    }
}
