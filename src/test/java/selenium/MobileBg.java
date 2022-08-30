package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class MobileBg {

    WebDriver driver;
    Actions actions;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeTest
    void setup() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--window-size=1920x1080");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);

        // implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

        // explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // fluent wait
        FluentWait fluentWait = new FluentWait<>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(15));
        fluentWait.pollingEvery(Duration.ofMillis(250));
        fluentWait.ignoring(NoSuchElementException.class);

        driver.manage().window().maximize();

        actions = new Actions(driver);
    }

    @AfterTest
    void afterTest() {
        driver.quit();
    }

    @Test
    public void testMobileBgSearch() throws InterruptedException {
        driver.get("https://www.mobile.bg/pcgi/mobile.cgi");

        By marka = By.cssSelector("select[name='marka']");
        By model = By.cssSelector("select[name='model']");
        By search = By.cssSelector("#button2");

        By kumSita = By.cssSelector("button[class='fc-button fc-cta-consent fc-primary-button']");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(kumSita)));
        driver.findElement(kumSita).click();

        Select markaDropdown = new Select(driver.findElement(marka));
        Select modelDropdown = new Select(driver.findElement(model));
        WebElement searchBtn = driver.findElement(search);

        markaDropdown.selectByVisibleText("Ford");
        modelDropdown.selectByVisibleText("Focus");

        searchBtn.click();

        Thread.sleep(3000);
    }
}
