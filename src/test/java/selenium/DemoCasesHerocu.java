package selenium;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DemoCasesHerocu {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor executor;

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
        driver.quit();
    }

    @Test
    public void testAddRemoveElements() {
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        By listElements = By.xpath("//div[@id='elements']/button");
//        By listElements = By.cssSelector("div[id='elements']>button");
        List<WebElement> listDeletes = driver.findElements(listElements);
        Assert.assertEquals(listDeletes.size(), 0);

        WebElement addElementBtn = driver.findElement(By.cssSelector(".example>button"));
        addElementBtn.click();
        addElementBtn.click();

        listDeletes = driver.findElements(listElements);
        Assert.assertEquals(listDeletes.size(), 2);

        listDeletes.get(0).click();

        listDeletes = driver.findElements(listElements);
        Assert.assertEquals(listDeletes.size(), 1);
    }

    @Test
    public void testBasicAuth() throws InterruptedException {
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");

        By baseTxtAuth = By.xpath("//p");
        Assert.assertEquals(driver.findElement(baseTxtAuth).getText(), "Congratulations! You must have the proper credentials.");

        Thread.sleep(2000);
    }

    @Test
    public void testCheckboxes() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        By checkboxes = By.cssSelector("#checkboxes input");
        WebElement checkboxOne = driver.findElements(checkboxes).get(0);
        WebElement checkboxTwo = driver.findElements(checkboxes).get(1);

        Assert.assertFalse(checkboxOne.isSelected());
        Assert.assertTrue(checkboxTwo.isSelected());

        checkboxOne.click();
        checkboxTwo.click();

        Assert.assertTrue(checkboxOne.isSelected());
        Assert.assertFalse(checkboxTwo.isSelected());
    }

    @Test
    public void testContextMenu() {
        driver.get("https://the-internet.herokuapp.com/context_menu");

        WebElement divHotSpot = driver.findElement(By.cssSelector("#hot-spot"));

        actions.contextClick(divHotSpot).perform();

        Alert alert = driver.switchTo().alert();

        Assert.assertEquals(alert.getText(), "You selected a context menu");
        alert.accept();

        driver.switchTo().defaultContent();

    }

    @Test
    public void testDragAndDrop() {
        driver.get("https://jqueryui.com/droppable/");

        WebElement iFrame = driver.findElement(By.xpath("//iframe"));

        driver.switchTo().frame(iFrame);

        WebElement elementA = driver.findElement(By.xpath("//div[@id='draggable']"));
        WebElement elementB = driver.findElement(By.xpath("//div[@id='droppable']"));

        WebElement dropTxt = driver.findElement(By.xpath("//div[@id='droppable']/p"));

        Assert.assertEquals(dropTxt.getText(), "Drop here");

        actions.dragAndDrop(elementA, elementB).perform();

        dropTxt = driver.findElement(By.xpath("//div[@id='droppable']/p"));

        Assert.assertEquals(dropTxt.getText(), "Dropped!");

        driver.switchTo().parentFrame();
    }

    @Test
    public void testDynamicContent() {
        driver.get("https://the-internet.herokuapp.com/dynamic_content");

        By rowsTexts = By.cssSelector(".large-10.columns:not(.large-centered)");
        By rowsImages = By.cssSelector(".large-2.columns:not(.large-centered)>img");

        List<WebElement> listTextElements = driver.findElements(rowsTexts);
        List<WebElement> listImagesElements = driver.findElements(rowsImages);

        List<String> listImages = new ArrayList<>();
        for (WebElement element : listImagesElements
        ) {
            listImages.add(element.getAttribute("src"));
        }
        // TBD
    }

    @Test
    public void testFloatingMenu() {
        driver.get("https://the-internet.herokuapp.com/floating_menu");

        By menu = By.xpath("//div[@id='menu']");
        Assert.assertTrue(driver.findElement(menu).isDisplayed());

        executor.executeScript("window.scrollBy(0, 3000)");

        Assert.assertTrue(driver.findElement(menu).isDisplayed());

        executor.executeScript("window.scrollBy(0, -2000)");

        Assert.assertTrue(driver.findElement(menu).isDisplayed());
    }

    @Test
    public void testHovers() {
        driver.get("https://the-internet.herokuapp.com/hovers");

        WebElement figureLeft = driver.findElement(By.xpath("//div[@class='example']/div[1]"));
        WebElement figureMiddle = driver.findElement(By.xpath("//div[@class='example']/div[2]"));
        WebElement figureRight = driver.findElement(By.xpath("//div[@class='example']/div[3]"));

        WebElement hoveredL = driver.findElement(By.xpath("//a[@href='/users/1']"));
        WebElement hoveredM = driver.findElement(By.xpath("//a[@href='/users/2']"));
        WebElement hoveredR = driver.findElement(By.xpath("//a[@href='/users/3']"));

        actions.moveToElement(figureLeft).perform();

        Assert.assertTrue(hoveredL.isDisplayed());
        Assert.assertFalse(hoveredM.isDisplayed());
        Assert.assertFalse(hoveredR.isDisplayed());

        actions.moveToElement(figureMiddle).perform();

        Assert.assertFalse(hoveredL.isDisplayed());
        Assert.assertTrue(hoveredM.isDisplayed());
        Assert.assertFalse(hoveredR.isDisplayed());

        actions.moveToElement(figureRight).perform();

        Assert.assertFalse(hoveredL.isDisplayed());
        Assert.assertFalse(hoveredM.isDisplayed());
        Assert.assertTrue(hoveredR.isDisplayed());
    }

    @Test
    public void testMultipleWindows() {
        driver.get("https://the-internet.herokuapp.com/windows");

        WebElement clickHereLink = driver.findElement(By.xpath("//a[@href='/windows/new']"));

        String originalWindow = driver.getWindowHandle();

        clickHereLink.click();

        for (String winHandle : driver.getWindowHandles()
        ) {
            driver.switchTo().window(winHandle);
        }

        String newWindow = driver.getWindowHandle();

        WebElement newWindowTxt = driver.findElement(By.xpath("//h3"));

        Assert.assertTrue(newWindowTxt.isDisplayed());
        Assert.assertEquals(newWindowTxt.getText(), "New Window");

        driver.switchTo().window(originalWindow);

        Assert.assertTrue(clickHereLink.isDisplayed());
        newWindowTxt = driver.findElement(By.xpath("//h3"));
        Assert.assertEquals(newWindowTxt.getText(), "Opening a new window");

        driver.switchTo().window(newWindow);
        newWindowTxt = driver.findElement(By.xpath("//h3"));
        Assert.assertEquals(newWindowTxt.getText(), "New Window");
    }

    @Test
    public void testRedirectLink() {
        driver.get("https://the-internet.herokuapp.com/redirector");

        WebElement redirectLink = driver.findElement(By.cssSelector("#redirect"));

        String originalUrl = driver.getCurrentUrl();
        Assert.assertEquals(originalUrl, "https://the-internet.herokuapp.com/redirector");

        redirectLink.click();

        String redirectedUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(redirectedUrl, originalUrl);
        Assert.assertEquals(redirectedUrl, "https://the-internet.herokuapp.com/status_codes");
    }

}
