package org.example;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class DromTest {
    public static MainPage mainPage;

    @Test

    public void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));        WebDriver driver = new ChromeDriver();
        mainPage = new MainPage(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("test_page"));

        mainPage.read_n_find();

        driver.quit();
    }
}
