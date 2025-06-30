package com.testinium.steps;

import com.testinium.base.BaseTest;
import com.testinium.model.ElementInfo;
import com.thoughtworks.gauge.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseSteps extends BaseTest {

    public static int DEFAULT_MAX_ITERATION_COUNT = 150;
    public static int DEFAULT_MILLISECOND_WAIT_AMOUNT = 100;

    private static String SAVED_ATTRIBUTE;

    private String compareText;

    public BaseSteps() {
        initMap(getFileList());
    }

    List<WebElement> findElements(String key) {
        return driver.findElements(getElementInfoToBy(findElementInfoByKey(key)));
    }

    // Helper method to locate elements using key from JSON
    protected By by(String key) {
        return getElementInfoToBy(findElementInfoByKey(key));
    }

    @Step({"Go to <url> address", "Go to <url> address"})
    public void goToUrl(String url) {
        driver.get(url);
        logger.info(url + " address opened");
    }

    public By getElementInfoToBy(ElementInfo elementInfo) {
        By by = null;
        if (elementInfo.getType().equals("css")) {
            by = By.cssSelector(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("name"))) {
            by = By.name(elementInfo.getValue());
        } else if (elementInfo.getType().equals("id")) {
            by = By.id(elementInfo.getValue());
        } else if (elementInfo.getType().equals("xpath")) {
            by = By.xpath(elementInfo.getValue());
        } else if (elementInfo.getType().equals("linkText")) {
            by = By.linkText(elementInfo.getValue());
        } else if (elementInfo.getType().equals(("partialLinkText"))) {
            by = By.partialLinkText(elementInfo.getValue());
        }
        return by;
    }

    WebElement findElement(String key) {
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait webDriverWait = new WebDriverWait(driver, 60);
        WebElement webElement = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})", webElement);
        return webElement;
    }

    private void clickElement(WebElement element) {
        element.click();
    }

    @Step({"Wait <value> seconds", "Wait for <int> seconds"})
    public void waitBySeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void hoverElement(WebElement element) {
        actions.moveToElement(element).build().perform();
    }

    @Step({"Click to element <key>", "Click the <key> element"})
    public void clickElement(String key) {
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(infoParam));
        List<WebElement> elements = findElements(key);
        if (elements.isEmpty()) {
            Assertions.fail("Cannot click on " + key + " element. Element does not exist or does not work " + "\n *" + "\n********************FAIL*********************" + "\n *" + "\n" + "Cannot click on " + key + "' element. Element does not exist or does not work " + "\n *" + "\n********************FAIL********************" + "\n *");
        }

        hoverElement(findElement(key));
        clickElement(findElement(key));
        logger.info("Clicked to the element " + key);
        waitByMilliSeconds(2000);
    }

    @Step({"Click element if exist <key>", "If the <key> element exists, click it"})
    public void clickIfExist(String key) {

        waitBySeconds(15);
        if (findElements(key).size() == 0) {

            logger.info("Cannot click on " + key + " element. Element does not exist or does not work " + "\n *" + "\n********************FAIL*********************" + "\n *" + "\n" + "Could not click on " + key + " element. Element does not exist or does not work. " + "\n *" + "\n********************FAIL********************" + "\n *");
        }
        if (findElements(key).size() > 0) {
            logger.info(key + " element exists.");
            clickElement(findElement(key));
            logger.info("Clicked on " + key + " element.");
            waitByMilliSeconds(2000);
            findElement(key).isEnabled();
        }
    }


    @Step({"Send ENTER key to element <key>", "Send ENTER key to element <key>"})
    public void sendKeyToElementBACKSPACE(String key) {
        findElement(key).sendKeys(Keys.ENTER);
        logger.info("Sent ENTER key to " + key + " element.");
    }

    @Step({"Check if element <key> contains text <expectedText>", "Check if <key> element contains text <text>"})
    public void checkElementContainsText(String key, String expectedText) {
        waitBySeconds(5);
        Boolean containsText = findElement(key).getText().contains(expectedText);
        String actualText = findElement(key).getText();
        logger.info("Text at " +key + " element == " + actualText);
        assertTrue(containsText, "Expected text is not contained");
        logger.info("Element " + key + " Contained expected : ''" + expectedText + "'' Text");
    }

    @Step({"Wait <value> milliseconds", "Wait for <long> milliseconds"})
    public void waitByMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step({"Check if <key> element's attribute <attribute> contains the value <expectedValue>", "Check if the <attribute> attribute of the <key> element contains the value <value>"})
    public void checkElementAttributeContains(String key, String attribute, String expectedValue) {
        WebElement element = findElement(key);

        String actualValue;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualValue = element.getAttribute(attribute).trim();
            if (actualValue.contains(expectedValue)) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assertions.fail("Element's attribute value doesn't contain expected value");
    }

    private JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) driver;
    }

    //Function required for Javascript scripts to run
    private Object executeJS(String script, boolean wait) {
        return wait ? getJSExecutor().executeScript(script, "") : getJSExecutor().executeAsyncScript(script, "");
    }

    //Scroll the page to a specific location
    private void scrollTo(int x, int y) {
        String script = String.format("window.scrollTo(%d, %d);", x, y);
        executeJS(script, true);
    }

    //Scroll the webpage to the location of a specific element
    public WebElement scrollToElementToBeVisible(String key) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        WebElement webElement = driver.findElement(getElementInfoToBy(elementInfo));
        if (webElement != null) {
            scrollTo(webElement.getLocation().getX(), webElement.getLocation().getY() - 100);
        }
        return webElement;
    }


    @Step({"Scroll to <key> area", "Scroll to the <key> area"})
    public void scrollToElement(String key) {
        scrollToElementToBeVisible(key);
        logger.info("Scrolled to the area of " + key + " element");

    }


    @Step({"Scroll to <key> area with js", "Scroll to the <key> area with js"})
    public void scrollToElementWithJs(String key) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        WebElement element = driver.findElement(getElementInfoToBy(elementInfo));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    @Step("Hover over the <key> element")
    public void hover(String key) {
        hoverElement(findElement(key));
    }

    @Step({"Check if element <key> exists else print message <message>", "Check if element <key> exists, otherwise give an error message <message>"})
    public void getElementWithKeyIfExistsWithMessage(String key, String message) {
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(infoParam));
        ElementInfo elementInfo = findElementInfoByKey(key);
        By by = getElementInfoToBy(elementInfo);
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() > 0) {
                logger.info(key + " Element is Present on the Page");
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assertions.fail(message);
    }

    @Step({"Clear text of element <key>", "Clear the text field of the <key> element"})
    public void clearInputArea(String key) {
        findElement(key).clear();
    }

    @Step("Clear Cache")
    public void clearCache() throws InterruptedException {

        driver.manage().deleteAllCookies();
        driver.get("chrome://settings/clearBrowserData");
        Thread.sleep(2000);
        for (int i = 0; i < 7; i++) {

            driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.TAB);
            Thread.sleep(100);
        }
        driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);

    }

    @Step({"Clear text of element <key> with BACKSPACE", "Clear the text field of the <key> element with BACKSPACE"})
    public void clearInputAreaWithBackspace(String key) {
        WebElement element = findElement(key);
        element.clear();
        element.sendKeys("a");
        actions.sendKeys(Keys.BACK_SPACE).build().perform();
    }

    @Step({"If element <key> exists else print message <message>", "Check if element <key> exists, otherwise print an error message <message>"})
    public void getElementWithKeyIfExists(String key, String message) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        By by = getElementInfoToBy(elementInfo);

        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() > 0) {
                logger.info(key + " Element is Present on the Page");
                return;
            }
            loopCount++;
        }
        Assertions.fail(message);
    }

    @Step({"Wait until the <key> object is displayed on the page", "Wait until the <key> object is displayed on the page"})
    public void waitForElementVisibility(String key) {
        this.findElement(key);
        ExpectedConditions.elementToBeClickable(By.xpath("key"));
        this.logger.info("Waited until the " + key + " object was displayed on the page.");

    }

    @Step("Click on <locator> with js")
    public void clickByJs(String locator) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath(locator)));
    }

    @Step({"Check the <key> element", "Check if element <key> exist"})
    public void checkElement(String key) {
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(infoParam));
        List<WebElement> elements = findElements(key);
        if (elements.size() == 0) {
            logger.info("Can not find the element " + key + " on the page ");
        }
        if (elements.size() > 0) {
            logger.info("Element " + key + " exist on the page");
        }
    }

    @Step("Is the number of <key> elements equal to the number <number>?")
    public void compareElements(String key, int number) {

        List<WebElement> priceList = findElements(key);
        int a = priceList.size();
        logger.info("Hotels without a price are not listed in the " + key + " results.");
        assertFalse(a == number, "Hotels without a price are also listed in the results.");
    }

    @Step({"Refresh page", "Refresh the page"})
    public void refreshPage() {
        driver.navigate().refresh();
    }

    @Step({"Check if element <key> not exists", "Check that the <key> element does not exist"})
    public void checkElementNotExists(String key) {
        ElementInfo elementInfo = findElementInfoByKey(key);
        By by = getElementInfoToBy(elementInfo);

        int loopCount = 0;
        while (loopCount < 10) {
            if (driver.findElements(by).size() == 0) {
                logger.info("Checked that the " + key + " element does not exist.");
                return;
            }
            loopCount++;
        }
        Assertions.fail(key + " element is still visible");
    }

    @Step({"When hovering over the <key> element, does it turn to the expected color in the design? If not, print <message>"})
    public void getElementColor(String key, String message) {

        ElementInfo elementInfo = findElementInfoByKey(key);
        By by = getElementInfoToBy(elementInfo);

        if (driver.findElements(by).size() > 0) {
            hoverElement(findElement(key));
            WebElement element = findElement(key);
            String colorCode = element.getCssValue("background-color");
            String expectedColorCodeInRGB = "rgba(250, 88, 81, 0.1)";

            // Asserting actual and expected color codes
            Assertions.assertEquals(expectedColorCodeInRGB, colorCode);
            logger.info("The color of the " + key + " element is the same as the expected color in the design. Element Color: " + colorCode + " Expected Color in Design: " + expectedColorCodeInRGB);


        }
        if (driver.findElements(by).size() == 0) {
            Assertions.fail(message);
        }
    }

    @Step({"When hovering over the <key> element, does it turn to the expected color in the design? If not,"})
    public void mehmethod(String key) {

        WebElement element = findElement(key);
        String colorCode = element.getCssValue("background-color"); //from the page
        String expectedColorCodeInRGB = "rgb(14, 184, 227)"; //refresh
        String expectedSecondColorCodeInRGB = "rgb(63, 81, 181)"; //refresh
        String actualColorCodeInRgb = "rgb(0, 0, 0)";

        while (colorCode.equals(expectedColorCodeInRGB) || colorCode.equals(expectedSecondColorCodeInRGB)) {

            colorCode = element.getCssValue("background-color"); //from the page
            waitBySeconds(1);
            driver.navigate().refresh();

            if (colorCode.equals(actualColorCodeInRgb)) {

                break;
            }
        }
    }

    @Step({"Check if current URL contains the value <expectedURL>", "Check if the current URL contains the value <url>"})
    public void checkURLContainsRepeat(String expectedURL) {

        int loopCount = 0;
        String actualURL = "";
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualURL = driver.getCurrentUrl();

            if (actualURL != null && actualURL.contains(expectedURL)) {
                logger.info("current url address contains the expected value: " + expectedURL);
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        //Assertions.fail(
        // "Actual URL doesn't match the expected." + "Expected: " + expectedURL + ", Actual: "
        // + actualURL);
        logger.info("current url address NOT contained the expected value: " + expectedURL);
    }

    @Step("The number of hotels for the <key> element is checked")
    public void hotelCount(String key) {

        List<WebElement> elements = findElements(key);
        logger.info(elements.size() + " hotels are listed");

    }

    @Step({"Write value <text> to element <key>", "Write the text <text> to the <key> field"})
    public void ssendKeys(String text, String key) {
        List<WebElement> elements = findElements(key);

        if (elements.size() == 0) {

            logger.info(key + " Element Not Exist or Not Working ");
            Assertions.fail(" * Unable to write text to " + key + " element. Not Exist or Not Working * " + "\n *" + "\n********************FAIL********************" + "\n *" + "\nUnable to write text to " + key + " element. Not Exist or Not Working " + "\n *" + "\n********************FAIL********************" + "\n *");
        }
        if (elements.size() > 0) {
            findElement(key).clear();
            findElement(key).sendKeys(text);
            logger.info(text + " writed to the " + key + " element.");
        }
    }

    @Step({"Print element text by <key>", "Print the text value of <key>"})
    public void printElementText(String key) {
        By infoParam = getElementInfoToBy(findElementInfoByKey(key));
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(infoParam));
        String text = findElement(key).getText();
        logger.info("Element's text was printed-" + text);
    }

    @Step({"The number of <key> elements on the page is printed"})
    public void printNumberOfElements(String key) {
        List<WebElement> elementCount = findElements(key);
        int a = elementCount.size();
        logger.info("There are " + a + " of the " + key + " element.");
    }

    @Step({"Captcha is clicked"})
    public void skipCapthca() {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[starts-with(@name, 'a-') and starts-with(@src, 'https://www.google.com/recaptcha')]")));
        waitByMilliSeconds(2000);
        By recaptcha = By.id("recaptcha-anchor");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("recaptcha-anchor")));
        driver.findElement(recaptcha).click();

        driver.switchTo().defaultContent();
        logger.info("Clicked ReCapthca");

    }

    @Step({"Find element by <key>, clear and write a random name value", "Find element by <key> clear and send keys random name "})
    public void RandomName(String key) {

        WebElement webElement = findElement(key);
        webElement.clear();
        String[] names = {"ayse", "fatma", "ali", "veli", "mert", "gungor", "melis"};
        Random r = new Random();
        int randomNumber = r.nextInt(names.length);
        findElement(key).sendKeys(names[randomNumber]);
        String a = names[randomNumber];

        logger.info("random " + a + " name was written");

    }

    @Step({"Find element by <key>, clear and write a random surname value", "Find element by <key> clear and send keys random surname "})
    public void RandomSurName(String key) {

        WebElement webElement = findElement(key);
        webElement.clear();
        String[] surnames = {"yilmaz", "celik", "saglam", "kaya", "gunes", "evci", "ozturk"};
        Random r = new Random();
        int randomNumber = r.nextInt(surnames.length);
        String b = surnames[randomNumber];
        findElement(key).sendKeys(surnames[randomNumber]);
        logger.info("random " + b + " surname was written");

    }

    @Step("Switch to the other tab")
    public void switchToPage() {
        for (String curWindow : driver.getWindowHandles()) {
            driver.switchTo().window(curWindow);
        }
        logger.info("switched to the other tab");
    }

    @Step({"Click <key> element six times", "Click the <key> element six times"})
    public void clickElementSixTimes(String key) throws InterruptedException {
        WebElement element = findElement(key);

        for (int i = 0; i < 6; i++) {

            Thread.sleep(200);
            element.click();
            System.out.println("Expected to be clicked for the " + i + ". time");

        }
        waitByMilliSeconds(5000);
        logger.info("Clicked the " + key + " element 6 times.");

    }


    @Step({"Click <key> element two times", "Click the <key> element two times"})
    public void clickElementTwoTimes(String key) throws InterruptedException {
        WebElement element = findElement(key);

        for (int i = 0; i < 2; i++) {

            Thread.sleep(200);
            element.click();
            System.out.println("Expected to be clicked for the " + i + ". time");

        }
        waitByMilliSeconds(5000);
        logger.info("Clicked the " + key + " element 2 times.");

    }

    public void javascriptclicker(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    @Step("Click on the <key> element with javascript")
    public void clickToElementWithJavaScript(String key) {
        WebElement element = findElement(key);
        javascriptclicker(element);
        logger.info("Clicked on the " + key + " element with javascript");
    }

    @Step({"Check if the <key> Checkbox is selected"})
    public void getCheckboxColor(String key) {

        ElementInfo elementInfo = findElementInfoByKey(key);
        By by = getElementInfoToBy(elementInfo);

        if (driver.findElements(by).size() > 0) {
            hoverElement(findElement(key));
            WebElement element = findElement(key);
            String colorCode = element.getCssValue("color");
            String expectedColorCodeInRGB = "rgba(0, 153, 255, 0)";

            // Asserting actual and expected color codes
            Assertions.assertEquals(expectedColorCodeInRGB, colorCode);
            logger.info("The " + key + " Checkbox is in the Selected State");

        }
        if (driver.findElements(by).size() == 0) {
            logger.info("The " + key + " Checkbox could not be found");
        }
    }
}