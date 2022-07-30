package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.math.BigInteger;
import java.util.List;

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
    BigInteger Z;
    @Then("The search engine finds all related results including results with ads")
    public void User_finds_search_results_and_checks_number_of_ads () {
        X = driver.findElement(By.id("result-stats")).getText().replaceAll("[^0-9]", "");
        System.out.println("There are about " + X + " results");
        Z = new BigInteger(X);
        if (Z.intValue() > 0)
        {
            System.out.println("Search results are greater than zero");
            List<WebElement> ADs = driver.findElements(By.xpath("//*[@id=\"tads\"]/div"));
            System.out.println("There are " + ADs.size() + " ADs in this page.");
        }
        else
        {
            System.out.println("There are no search results");
        }
        driver.quit();
    }
}