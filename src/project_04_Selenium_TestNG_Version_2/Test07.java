package project_04_Selenium_TestNG_Version_2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utility.BaseDriver;

public class Test07 extends BaseDriver {


    @Test
    @Parameters("searchedWord")
    void parameterizedSearchTest(String searchedWord) {

        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        WebElement searchBox = driver.findElement(By.id("small-searchterms"));
        searchBox.sendKeys(searchedWord);

        WebElement searchButton = driver.findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        WebElement adobe = driver.findElement(By.xpath("//h2[@class='product-title']/a"));

        Assert.assertTrue(adobe.getText().contains("Adobe Photoshop CS4"));
    }
}


