package utilities;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseDriverParameter {

    public static WebDriverWait wait;
    public static Faker randomGenerator;
    public WebDriver driver;

    @BeforeClass
    @Parameters("browserType")
    public void initialOperations(String browserType) {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.SEVERE);

        switch (browserType.toLowerCase()) {

            case "firefox":
                driver = new FirefoxDriver();
                break;

            case "safari":
                driver = new SafariDriver();
                break;

            case "edge":
                driver = new EdgeDriver();
                break;

            default:
                driver = new ChromeDriver();
        }
        driver.manage().window().maximize(); // It maximizes the screen.
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30)); // 30 sec delay: time to load the page
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));  // 30 sec delay: time to find the element
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        randomGenerator = new Faker();
    }

    @AfterClass
    public void finishingOperations() {
        driver.quit();
    }
}
