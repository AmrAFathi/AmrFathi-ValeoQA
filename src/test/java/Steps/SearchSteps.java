package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchSteps {
    WebDriver driver;

    @Given("User opens the browser and navigates to the search engine website")
    public void User_opens_website () {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://google.com");
    }

    @When("User types the search key \"Cars in London\" and clicks on the \"Search\" button")
    public void User_enters_searchkey() {
        driver.findElement(By.name("q")).sendKeys("Cars in London");
        driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[3]/center/input[1]")).click();
    }
    String X;

    int Y;
    BigInteger Z;
    @Then("The search engine finds results including ads and the system opens them and checks their titles")
    public void System_finds_search_results_and_checks_ads () {

        //Find the number of search results
        X = driver.findElement(By.id("result-stats")).getText().replaceAll("[^0-9]", "");
        System.out.println("There are about " + X + " results");
        Z = new BigInteger(X);

        //Checking if there are search results the system will find all results with ADs and count them
        if (Z.longValue() > 0) {
            System.out.println("Search results are greater than zero");
            List<WebElement> ADs = driver.findElements(By.xpath("//*[@id=\"tads\"]/div"));
            System.out.println("There are " + ADs.size() + " ADs in this page.");
            Y=ADs.size();

            //System will open each AD and check its title then will print it
            for (int i = Y; i > 0; i--) {
                driver.findElement(By.xpath("//*[@id=\"tads\"]/div["+i+"]/div/div/div/div[1]/a/div[2]/span[1]")).click();
                driver.manage().timeouts().getPageLoadTimeout();
                Assert.assertNotNull(driver.getTitle());
                System.out.println("Title is displayed successfully and it is : "+driver.getTitle());
                driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                driver.navigate().back();
            }
        }
        else if (Z.intValue() < 0) {
            System.out.println("There are no search results");
        }
        driver.quit();
        } }