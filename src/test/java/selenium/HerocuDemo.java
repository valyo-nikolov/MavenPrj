package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * This is demo test class including all homework scenarios from Heroku site
 */
public class HerocuDemo {

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
    public void testAddRemoveElements() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addElementsBtn = driver.findElement(By.cssSelector("div.example>button"));

        By listElements = By.xpath("//div[@id='elements']//descendant::button");
        List<WebElement> webElementList = driver.findElements(listElements);
        Assert.assertTrue(webElementList.size() == 0);

        addElementsBtn.click();
        addElementsBtn.click();

        webElementList = driver.findElements(listElements);
        Assert.assertTrue(webElementList.size() == 2);

        webElementList.forEach(button -> {
            Assert.assertTrue(button.isDisplayed());
            Assert.assertEquals(button.getText(), "Delete");
        });

        webElementList.get(0).click();

        webElementList = driver.findElements(listElements);
        Assert.assertTrue(webElementList.size() == 1);

        Thread.sleep(3000);
    }

    @Test()
    public void testBasicAuthentication() throws InterruptedException {
//        for (int i = 0; i < 1000; i++) {
//            driver.get("https://public:Lab008_haxors@190722top10.courses.cyreslab.org/bac/dirref2/index.php?userid=" + i);
//        }

        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");

        //sample using sibling
        By baseAuthTxt = By.xpath("//h3/following::p");
        Assert.assertEquals(driver.findElement(baseAuthTxt).getText(), "Congratulations! You must have the proper credentials.");

        Thread.sleep(2000);
    }

    @Test
    public void testCheckboxes() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        //sample with css selector
        By checkboxes = By.cssSelector("#checkboxes input");
        WebElement checkboxOne = driver.findElements(checkboxes).get(0);
        WebElement checkboxTwo = driver.findElements(checkboxes).get(1);

        Assert.assertFalse(checkboxOne.isSelected());
        Assert.assertTrue(checkboxTwo.isSelected());

        checkboxOne.click();
        checkboxTwo.click();

        Assert.assertTrue(checkboxOne.isSelected());
        Assert.assertFalse(checkboxTwo.isSelected());

        Thread.sleep(2000);
    }

    @Test
    public void testContextMenu() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/context_menu");

        WebElement divHotSpot = driver.findElement(By.cssSelector("#hot-spot"));

        actions.contextClick(divHotSpot).perform();

        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(), "You selected a context menu");
        alert.accept();

        driver.switchTo().defaultContent();

        Thread.sleep(5000);
    }

    @Test
    public void testDisappearingElements() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/disappearing_elements");

        By elements = By.xpath("//div[@class='example']//a");
        List<WebElement> webElementList = driver.findElements(elements);

        if (webElementList.size() == 5) {
            Assert.assertEquals(webElementList.get(0).getAttribute("href"), "https://the-internet.herokuapp.com/");
            Assert.assertEquals(webElementList.get(1).getAttribute("href"), "https://the-internet.herokuapp.com/about/");
            Assert.assertEquals(webElementList.get(2).getAttribute("href"), "https://the-internet.herokuapp.com/contact-us/");
            Assert.assertEquals(webElementList.get(3).getAttribute("href"), "https://the-internet.herokuapp.com/portfolio/");
            Assert.assertEquals(webElementList.get(4).getAttribute("href"), "https://the-internet.herokuapp.com/gallery/");
        } else {
            Assert.assertTrue(webElementList.size() == 4);
            Assert.assertEquals(webElementList.get(0).getAttribute("href"), "https://the-internet.herokuapp.com/");
            Assert.assertEquals(webElementList.get(1).getAttribute("href"), "https://the-internet.herokuapp.com/about/");
            Assert.assertEquals(webElementList.get(2).getAttribute("href"), "https://the-internet.herokuapp.com/contact-us/");
            Assert.assertEquals(webElementList.get(3).getAttribute("href"), "https://the-internet.herokuapp.com/portfolio/");
        }

        Thread.sleep(2000);

        driver.navigate().refresh();

        webElementList = driver.findElements(elements);

        if (webElementList.size() == 5) {
            Assert.assertEquals(webElementList.get(0).getAttribute("href"), "https://the-internet.herokuapp.com/");
            Assert.assertEquals(webElementList.get(1).getAttribute("href"), "https://the-internet.herokuapp.com/about/");
            Assert.assertEquals(webElementList.get(2).getAttribute("href"), "https://the-internet.herokuapp.com/contact-us/");
            Assert.assertEquals(webElementList.get(3).getAttribute("href"), "https://the-internet.herokuapp.com/portfolio/");
            Assert.assertEquals(webElementList.get(4).getAttribute("href"), "https://the-internet.herokuapp.com/gallery/");
        } else {
            Assert.assertTrue(webElementList.size() == 4);
            Assert.assertEquals(webElementList.get(0).getAttribute("href"), "https://the-internet.herokuapp.com/");
            Assert.assertEquals(webElementList.get(1).getAttribute("href"), "https://the-internet.herokuapp.com/about/");
            Assert.assertEquals(webElementList.get(2).getAttribute("href"), "https://the-internet.herokuapp.com/contact-us/");
            Assert.assertEquals(webElementList.get(3).getAttribute("href"), "https://the-internet.herokuapp.com/portfolio/");
        }

        Thread.sleep(2000);
    }

    @Test
    public void testSwitchIframePlusDragAndDrop() {
        driver.get("https://jqueryui.com/droppable/");

        Actions actions = new Actions(driver);

        WebElement iFrame =
                driver.findElement(By.xpath("//iframe[@src='/resources/demos/droppable/default.html']"));

        driver.switchTo().frame(iFrame);

        WebElement elementA = driver.findElement(By.xpath("//div[@id='draggable']"));
        WebElement elementB = driver.findElement(By.xpath("//div[@id='droppable']"));

        WebElement droppedTxt = driver.findElement(By.xpath("//div[@id='droppable']/p"));

        Assert.assertEquals(droppedTxt.getText(), "Drop here");

        Action action = actions.dragAndDrop(elementA, elementB).release(elementA).build();

        action.perform();

        droppedTxt = driver.findElement(By.xpath("//div[@id='droppable']/p"));

        Assert.assertEquals(droppedTxt.getText(), "Dropped!");
        driver.switchTo().defaultContent();
    }

    @Test
    public void testDropdown() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/dropdown");

        Select dropdown = new Select(driver.findElement(By.xpath("//select[@id='dropdown']")));
        dropdown.selectByVisibleText("Option 2");
        List<WebElement> listOfOptions = dropdown.getOptions();

        Assert.assertFalse(listOfOptions.get(1).isSelected());
        Assert.assertTrue(listOfOptions.get(2).isSelected());

        Thread.sleep(2000);
    }

//----------------------

    // Не вички елементи са различни всеки път!!!
    @Test
    public void testDynamicContent() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/dynamic_content");

        By rowsTexts = By.xpath("//div[@class='large-10 columns']");
        By rowsImages = By.xpath("//div[@class='large-2 columns']/img");

        List<WebElement> listTextsElements = driver.findElements(rowsTexts);
        List<WebElement> listImagesElements = driver.findElements(rowsImages);

        List<String> listTexts = new ArrayList<>();
        for (WebElement element : listTextsElements
        ) {
            listTexts.add(element.getText());
        }

        List<String> listImages = new ArrayList<>();
        for (WebElement element : listImagesElements
        ) {
            listImages.add(element.getAttribute("src"));
        }

        // Рефрешваме елементите
        WebElement clickHere = driver.findElement(By.xpath("//a[contains(@href, 'static')]"));
        clickHere.click();

        List<WebElement> listTextsAfter = driver.findElements(rowsTexts);
        List<WebElement> listImagesAfter = driver.findElements(rowsImages);

        for (int i = 0; i < listTextsAfter.size(); i++) {
            System.out.println("XXX: " + listTexts.get(i));
            System.out.println("XXX2: " + listTextsAfter.get(i).getText());
            Assert.assertNotEquals(listTexts.get(i), listTextsAfter.get(i).getText());
        }

        for (int i = 0; i < listImagesAfter.size(); i++) {
            System.out.println("YYY: " + listImages.get(i));
            System.out.println("YYY2: " + listImagesAfter.get(i).getAttribute("src"));
            Assert.assertNotEquals(listImages.get(i), listImagesAfter.get(i).getAttribute("src"));
        }

        Thread.sleep(2000);
    }

    @Test
    public void testDynamicControls() {
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

        By checkbox = By.xpath("//input[@type='checkbox']");
        By remove = By.cssSelector("button[type='button']");

        WebElement removeBtn = driver.findElement(remove);

        Assert.assertTrue(driver.findElement(checkbox).isDisplayed());
        Assert.assertTrue(removeBtn.isDisplayed());

        removeBtn.click();

        By loading = By.xpath("//div[@id='loading']");
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(loading)));

        By itsGoneTxt = By.xpath("//p[@id='message']");
        Assert.assertTrue(driver.findElement(itsGoneTxt).isDisplayed());

        By addBtn = By.xpath("//button[text()='Add']");
        Assert.assertTrue(driver.findElement(addBtn).isDisplayed());
    }

    // Example with JS Executor
    @Test
    public void testFloatingMenu() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/floating_menu");

        By menu = By.xpath("//div[@id='menu']");
        Assert.assertTrue(driver.findElement(menu).isDisplayed());

        Thread.sleep(3000);

        js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,3000)");

        Thread.sleep(3000);

        Assert.assertTrue(driver.findElement(menu).isDisplayed());

        js.executeScript("window.scrollBy(0,-1500)");

        Thread.sleep(3000);

        Assert.assertTrue(driver.findElement(menu).isDisplayed());
    }

    @Test
    public void testHovers() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/hovers");

        WebElement figureLeft = driver.findElement(By.xpath("//div[@class='example']/div[1]"));
        WebElement figureMiddle = driver.findElement(By.xpath("//div[@class='example']/div[2]"));
        WebElement figureRight = driver.findElement(By.xpath("//div[@class='example']/div[3]"));

        By hoveredL = By.xpath("//div[@class='example']/div[1]//h5");
        By hoveredM = By.xpath("//div[@class='example']/div[2]//h5");
        By hoveredR = By.xpath("//div[@class='example']/div[3]//h5");

        actions.moveToElement(figureLeft).perform();

        Assert.assertTrue(driver.findElement(hoveredL).isDisplayed());
        Assert.assertFalse(driver.findElement(hoveredM).isDisplayed());
        Assert.assertFalse(driver.findElement(hoveredR).isDisplayed());

        Thread.sleep(2000);

        actions.moveToElement(figureMiddle).perform();

        Assert.assertFalse(driver.findElement(hoveredL).isDisplayed());
        Assert.assertTrue(driver.findElement(hoveredM).isDisplayed());
        Assert.assertFalse(driver.findElement(hoveredR).isDisplayed());

        Thread.sleep(2000);

        actions.moveToElement(figureRight).perform();

        Assert.assertFalse(driver.findElement(hoveredL).isDisplayed());
        Assert.assertFalse(driver.findElement(hoveredM).isDisplayed());
        Assert.assertTrue(driver.findElement(hoveredR).isDisplayed());

        Thread.sleep(2000);
    }

    @Test
    public void testWindows() {
        driver.get("https://the-internet.herokuapp.com/windows");

        WebElement clickHereLink = driver.findElement(By.xpath("//a[@href='/windows/new']"));

        String originalWindow = driver.getWindowHandle();

        Assert.assertTrue(clickHereLink.isDisplayed());

        clickHereLink.click();

        // Switch to new window opened
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        String newWindow = driver.getWindowHandle();

        WebElement newWindowTxt = driver.findElement(By.xpath("//h3"));
        Assert.assertTrue(newWindowTxt.isDisplayed());
        Assert.assertEquals(newWindowTxt.getText(), "New Window");

        driver.switchTo().window(originalWindow);
        Assert.assertTrue(clickHereLink.isDisplayed());

        driver.switchTo().window(newWindow);
        Assert.assertEquals(newWindowTxt.getText(), "New Window");
    }

    @Test
    public void testRedirectLink() {
        driver.get("https://the-internet.herokuapp.com/redirector");

        WebElement redirector = driver.findElement(By.xpath("//a[@href='redirect']"));
        Assert.assertTrue(redirector.isDisplayed());
        String originalUrl = driver.getCurrentUrl();
        Assert.assertEquals(originalUrl, "https://the-internet.herokuapp.com/redirector");
        redirector.click();

        String redirectedUrl = driver.getCurrentUrl();
        Assert.assertEquals(redirectedUrl, "https://the-internet.herokuapp.com/status_codes");
    }

    @Test
    public void testNestedFrames() {
        driver.get("https://the-internet.herokuapp.com/nested_frames");

        WebElement frameTop = driver.findElement(By.xpath("//frame[@name='frame-top']"));
        WebElement frameBottom = driver.findElement(By.xpath("//frame[@name='frame-bottom']"));

        driver.switchTo().frame(frameTop);

        WebElement frameLeft = driver.findElement(By.xpath("//frame[@name='frame-left']"));
        WebElement frameMiddle = driver.findElement(By.xpath("//frame[@name='frame-middle']"));
        WebElement frameRight = driver.findElement(By.xpath("//frame[@name='frame-right']"));

        By leftTxt = By.xpath("//body[contains(text(), 'LEFT')]");
        By middleTxt = By.xpath("//div[contains(text(), 'MIDDLE')]");
        By rightTxt = By.xpath("//body[contains(text(), 'RIGHT')]");
        By bottomTxt = By.xpath("//body[contains(text(), 'BOTTOM')]");

        driver.switchTo().frame(frameLeft);
        Assert.assertTrue(driver.findElement(leftTxt).isDisplayed());

        driver.switchTo().parentFrame();
        driver.switchTo().frame(frameMiddle);
        Assert.assertTrue(driver.findElement(middleTxt).isDisplayed());

        driver.switchTo().parentFrame();
        driver.switchTo().frame(frameRight);
        Assert.assertTrue(driver.findElement(rightTxt).isDisplayed());

        driver.switchTo().parentFrame();
        driver.switchTo().parentFrame();
        driver.switchTo().frame(frameBottom);
        Assert.assertTrue(driver.findElement(bottomTxt).isDisplayed());
    }


    //  public static void dragAndDropJS(WebElement source, WebElement destination, WebDriver driver)
    // throws Exception
    //  {
    //    JavascriptExecutor js = (JavascriptExecutor) driver;
    //    js.executeScript("function createEvent(typeOfEvent) {\n" +"var event
    // =document.createEvent(\"CustomEvent\");\n" +"event.initCustomEvent(typeOfEvent,true, true,
    // null);\n" +"event.dataTransfer = {\n" +"data: {},\n" +"setData: function (key, value) {\n"
    // +"this.data[key] = value;\n" +"},\n" +"getData: function (key) {\n" +"return this.data[key];\n"
    // +"}\n" +"};\n" +"return event;\n" +"}\n" +"\n" +"function dispatchEvent(element,
    // event,transferData) {\n" +"if (transferData !== undefined) {\n" +"event.dataTransfer =
    // transferData;\n" +"}\n" +"if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
    // +"} else if (element.fireEvent) {\n" +"element.fireEvent(\"on\" + event.type, event);\n" +"}\n"
    // +"}\n" +"\n" +"function simulateHTML5DragAndDrop(element, destination) {\n" +"var
    // dragStartEvent =createEvent('dragstart');\n" +"dispatchEvent(element, dragStartEvent);\n" +"var
    // dropEvent = createEvent('drop');\n" +"dispatchEvent(destination,
    // dropEvent,dragStartEvent.dataTransfer);\n" +"var dragEndEvent = createEvent('dragend');\n"
    // +"dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" +"}\n" +"\n" +"var source =
    // arguments[0];\n" +"var destination = arguments[1];\n"
    // +"simulateHTML5DragAndDrop(source,destination);",source, destination);
    //    Thread.sleep(1500);
    //
    //  }

    //  @Test
    public void myTestDragAndDrop() {

        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        Actions actions = new Actions(driver);

        WebElement elementA = driver.findElement(By.xpath("//div[@id='column-a']"));
        WebElement elementB = driver.findElement(By.xpath("//div[@id='column-b']"));
        WebElement elementAHeader = driver.findElement(By.xpath("//div[@id='column-a']/header"));
        WebElement elementBHeader = driver.findElement(By.xpath("//div[@id='column-b']/header"));

        List<WebElement> elements =
                driver.findElements(By.xpath("//div[@id='columns']/descendant::header"));

        elements.forEach(e -> System.out.println("XXX: " + e.getText()));

        Assert.assertEquals(elementAHeader.getText(), elements.get(0).getText());
        Assert.assertEquals(elementBHeader.getText(), elements.get(1).getText());

        actions.clickAndHold(elementAHeader).moveToElement(elementB).release().perform();

        WebElement script = driver.findElement(By.xpath("//div[@id='content']/script"));
        System.out.println("XXX: " + script.isEnabled());


        Action action = actions.dragAndDrop(elementA, elementB).release(elementA).build();
        action.perform();

        //    try {
        //      dragAndDropJS(elementAHeader, elementBHeader, driver);
        //    } catch (Exception e) {
        //      throw new RuntimeException(e);
        //    }

        //    actions
        //        .moveToElement(elementAHeader)
        //        .pause(Duration.ofSeconds(1))
        //        .clickAndHold(elementAHeader)
        //        .pause(Duration.ofSeconds(1))
        //        .moveByOffset(elementB.getSize().getWidth() * 2 - 2, elementB.getSize().getHeight())
        //        .moveToElement(elementB)
        //        .moveByOffset(elementB.getSize().getWidth() * 2 - 2, elementB.getSize().getHeight())
        //        .pause(Duration.ofSeconds(1))
        //        .release(elementAHeader)
        //        .build()
        //        .perform();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        elements = driver.findElements(By.xpath("//div[@id='columns']/descendant::header"));
        elements.forEach(e -> System.out.println("YYY: " + e.getText()));

        Assert.assertEquals(elementAHeader.getText(), elements.get(1).getText());
        Assert.assertEquals(elementBHeader.getText(), elements.get(0).getText());
    }
}
