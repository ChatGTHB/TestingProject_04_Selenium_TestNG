package utilities;

import org.openqa.selenium.*;
import org.testng.Assert;

public class Tools {

    public static void wait(int sn) {
        try {
            Thread.sleep(1000L * sn);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void successMessageValidation() {
        WebElement messageLabel = BaseDriver.driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible']"));
        Assert.assertTrue(messageLabel.getText().toLowerCase().contains("success"));
    }
}
