package project_04_Selenium_TestNG_Version_2;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.BaseDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestsFrom01To06 extends BaseDriver {

    String mail, password;

    @Test(priority = 1)
    void registrationTest() {

        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
        mail = randomGenerator.internet().emailAddress();
        password = randomGenerator.internet().password();

        WebElement registerLink = driver.findElement(By.xpath("//a[text()='Register']"));
        registerLink.click();

        WebElement firstName = driver.findElement(By.id("FirstName"));
        firstName.sendKeys(randomGenerator.name().firstName());

        WebElement lastName = driver.findElement(By.id("LastName"));
        lastName.sendKeys(randomGenerator.name().lastName());

        WebElement dayMenu = driver.findElement(By.cssSelector("[name='DateOfBirthDay']"));
        Select day = new Select(dayMenu);
        wait.until(ExpectedConditions.elementToBeClickable(dayMenu));
        day.selectByIndex(11);

        WebElement monthMenu = driver.findElement(By.cssSelector("[name='DateOfBirthMonth']"));
        Select month = new Select(monthMenu);
        wait.until(ExpectedConditions.elementToBeClickable(monthMenu));
        month.selectByIndex(12);

        WebElement yearMenu = driver.findElement(By.cssSelector("[name='DateOfBirthYear']"));
        Select year = new Select(yearMenu);
        wait.until(ExpectedConditions.elementToBeClickable(yearMenu));
        year.selectByVisibleText("1993");

        WebElement eMail = driver.findElement(By.id("Email"));
        eMail.sendKeys(mail);

        WebElement passwordElement = driver.findElement(By.id("Password"));
        passwordElement.sendKeys(password);

        WebElement confirmPassword = driver.findElement(By.id("ConfirmPassword"));
        confirmPassword.sendKeys(password);

        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Register']"));
        registerButton.click();

        WebElement verificationMessage = driver.findElement(By.xpath("//div[text()='Your registration completed']"));

        Assert.assertEquals(verificationMessage.getText(), "Your registration completed");
    }

    @Test(priority = 2, dependsOnMethods = {"registrationTest"})
    void loginTest() {

        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        WebElement loginLink = driver.findElement(By.xpath("//a[text()='Log in']"));
        loginLink.click();

        WebElement eMail = driver.findElement(By.id("Email"));
        eMail.sendKeys(mail);

        WebElement passwordElement = driver.findElement(By.id("Password"));
        passwordElement.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Log in']"));
        loginButton.click();

        WebElement logoutLink = driver.findElement(By.xpath("//a[text()='Log out']"));

        Assert.assertEquals(logoutLink.getText(), "Log out");

        logoutLink.click();
    }

    @Test(dataProvider = "getData", dependsOnMethods = {"registrationTest"}, priority = 3)
    void dataProviderLoginTest(String eMailPro, String passwordPro) {

        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        WebElement loginLink = driver.findElement(By.xpath("//a[text()='Log in']"));
        loginLink.click();

        WebElement eMail = driver.findElement(By.id("Email"));
        eMail.sendKeys(eMailPro);

        WebElement passwordElement = driver.findElement(By.id("Password"));
        passwordElement.sendKeys(passwordPro);

        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Log in']"));
        loginButton.click();

        SoftAssert softAssert = new SoftAssert();

        if (passwordPro.equals(password) && eMailPro.equals(mail)) {
            WebElement logoutLink = driver.findElement(By.xpath("//a[text()='Log out']"));
            softAssert.assertEquals(logoutLink.getText(), "Log out");
            logoutLink.click();
        } else {
            WebElement labelMessage = driver.findElement(By.cssSelector("div[class='message-error validation-summary-errors']"));
            softAssert.assertEquals(labelMessage.getText(), "Login was unsuccessful. Please correct the errors and try again.\n" + "No customer account found");
        }
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] getData() {

        Object[][] data = {{mail, password}, {randomGenerator.internet().emailAddress(), randomGenerator.internet().password()},};
        return data;
    }

    @Test(priority = 4)
    void tabMenuTest() {

        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        List<String> tabMenuList = new ArrayList<>(Arrays.asList("Computers", "Electronics", "Apparel", "Digital downloads", "Books", "Jewelry", "Gift Cards"));

        List<WebElement> tabMenu = driver.findElements(By.xpath("//ul[@class='top-menu notmobile']/li"));

        for (int i = 0; i < tabMenu.size(); i++) {
            Assert.assertTrue(tabMenu.get(i).getText().contains(tabMenuList.get(i)));
        }
    }

    @Test(priority = 5)
    void orderGiftTest() {

        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        WebElement giftCards = driver.findElement(By.xpath("(//ul[@class='top-menu notmobile']/li)[7]"));
        giftCards.click();

        List<WebElement> physicalGiftCards = driver.findElements(By.xpath(".//a[contains(text(),'Physical')]"));
        physicalGiftCards.get(((int) (Math.random() * physicalGiftCards.size()))).click();

        WebElement recipientName = driver.findElement(By.className("recipient-name"));

        Actions actions = new Actions(driver);
        Action action = actions.moveToElement(recipientName).click().sendKeys("Recipient's name").keyDown(Keys.TAB).keyUp(Keys.TAB).sendKeys("My name").keyDown(Keys.TAB).keyUp(Keys.TAB).sendKeys("My message").build();
        action.perform();

        WebElement addToCart = driver.findElement(By.xpath("(//button[@type='button'])[1]"));
        addToCart.click();

        WebElement verificationMessage = driver.findElement(By.xpath("//p[text()='The product has been added to your ']"));

        Assert.assertEquals(verificationMessage.getText(), "The product has been added to your shopping cart");
    }

    @Test(priority = 6)
    void orderComputerTest() {

        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        Actions actions = new Actions(driver);

        WebElement computers = driver.findElement(By.xpath("(//a[text()='Computers '])[1]"));

        Action action = actions.moveToElement(computers).build();
        action.perform();

        WebElement desktops = driver.findElement(By.xpath("(//a[text()='Desktops '])[1]"));
        desktops.click();

        WebElement buildComputer = driver.findElement(By.xpath("//a[text()='Build your own computer']"));
        buildComputer.click();

        WebElement ramSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("product_attribute_2")));

        action = actions.moveToElement(ramSelect).build();
        action.perform();

        List<WebElement> ramOptions = driver.findElements(By.cssSelector("select[id='product_attribute_2']>option"));
        ramOptions.get((int) (Math.random() * ramOptions.size() - 1) + 1).click();

        List<WebElement> hdd = driver.findElements(By.xpath("//input[@name='product_attribute_3']"));
        hdd.get((int) (Math.random() * hdd.size())).click();

        WebElement addToCart = driver.findElement(By.id("add-to-cart-button-1"));
        addToCart.click();

        WebElement verificationMessage = driver.findElement(By.xpath("//p[text()='The product has been added to your ']"));

        Assert.assertEquals(verificationMessage.getText(), "The product has been added to your shopping cart");
    }
}


