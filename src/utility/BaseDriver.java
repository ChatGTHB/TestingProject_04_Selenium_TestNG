package utility;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.*;

public class BaseDriver {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Faker randomGenerator;

    @BeforeClass
    public void startingOperations() {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.SEVERE);

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        randomGenerator = new Faker();
    }

    @AfterClass
    public void endingOperations() {
        driver.quit();
    }
}
