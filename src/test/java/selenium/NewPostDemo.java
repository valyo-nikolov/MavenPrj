package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class NewPostDemo {

    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;
    public JavascriptExecutor executor;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--window-size=1920x1080");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        // Explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // Init Actions class
        actions = new Actions(driver);
        executor = (JavascriptExecutor) driver;
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testNewPost() throws InterruptedException {

        driver.get("http://training.skillo-bg.com/users/login");

        WebElement userName = driver.findElement(By.cssSelector("#defaultLoginFormUsername"));
        WebElement password = driver.findElement(By.cssSelector("#defaultLoginFormPassword"));
        WebElement signInBtn = driver.findElement(By.cssSelector("#sign-in-button"));

        userName.sendKeys("Valyo1");
        password.sendKeys("valyo1");
        signInBtn.click();

        By logoutLink = By.cssSelector(".fas.fa-sign-out-alt.fa-lg");

        Assert.assertTrue(driver.findElement(logoutLink).isDisplayed());

        WebElement newPostLink = driver.findElement(By.cssSelector("#nav-link-new-post"));
        newPostLink.click();

        WebElement browseBtn = driver.findElement(By.cssSelector(".file.ng-untouched.ng-pristine.ng-invalid"));
        browseBtn.sendKeys("/home/valentin/Pictures/google-doodle.png");

        WebElement switchType = driver.findElement(By.cssSelector("label[for]"));
//        switchType.click();

//        Thread.sleep(3000);

        WebElement createPostBtn = driver.findElement(By.cssSelector("#create-post"));
        createPostBtn.click();

//        Thread.sleep(2000);
    }
}
