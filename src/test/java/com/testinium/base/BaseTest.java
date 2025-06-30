package com.testinium.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testinium.model.ElementInfo;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class BaseTest {

    protected static WebDriver driver;
    protected static Actions actions;
    protected static WebDriverWait wait;

    protected Logger logger = LoggerFactory.getLogger(getClass());
    DesiredCapabilities capabilities;
    ChromeOptions chromeOptions;
    FirefoxOptions firefoxOptions;

    String browserName = "chrome";
    String selectPlatform = "mac";

    private static final String DEFAULT_DIRECTORY_PATH = "elementValues";
    ConcurrentMap<String, Object> elementMapList = new ConcurrentHashMap<>();

    @BeforeScenario
    public void setUp() {
        logger.info("************************************  BeforeScenario  ************************************");
        try {
            if (StringUtils.isEmpty(System.getenv("key"))) {
                logger.info("Local cihazda " + selectPlatform + " ortamında " + browserName + " browserında test ayağa kalkacak");
                if ("win".equalsIgnoreCase(selectPlatform)) {
                    if ("chrome".equalsIgnoreCase(browserName)) {
                        driver = new ChromeDriver(chromeOptions());
                    } else if ("firefox".equalsIgnoreCase(browserName)) {
                        driver = new FirefoxDriver(firefoxOptions());
                    }
                } else if ("mac".equalsIgnoreCase(selectPlatform)) {
                    if ("chrome".equalsIgnoreCase(browserName)) {
                        driver = new ChromeDriver(chromeOptions());
                    } else if ("firefox".equalsIgnoreCase(browserName)) {
                        driver = new FirefoxDriver(firefoxOptions());
                    }
                }
                actions = new Actions(driver);
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

            } else {
                logger.info("************************************   Testiniumda test ayağa kalkacak   ************************************");
                ChromeOptions options = new ChromeOptions();
                capabilities = DesiredCapabilities.chrome();

                String PROXY = "ec2-54-154-66-64.eu-west-1.compute.amazonaws.com:3128";
                Proxy proxy = new Proxy();
                //Proxy proxy = new org.openqa.selenium.Proxy();
                proxy.setProxyType(Proxy.ProxyType.MANUAL);
                proxy.setHttpProxy(PROXY);
                proxy.setFtpProxy(PROXY);
                proxy.setSslProxy(PROXY);
                capabilities.setCapability(CapabilityType.PROXY, proxy);
                LoggingPreferences loggingprefs = new LoggingPreferences();
                loggingprefs.enable(LogType.BROWSER, Level.ALL);
                loggingprefs.enable(LogType.CLIENT, Level.ALL);
                loggingprefs.enable(LogType.PERFORMANCE, Level.ALL);
                loggingprefs.enable(LogType.PROFILER, Level.ALL);
                options.setExperimentalOption("w3c", false);
                options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
                options.addArguments("disable-translate");
                options.addArguments("--disable-notifications");
                options.addArguments("--start-fullscreen");
                Map<String, Object> prefs = new HashMap<>();
                options.setExperimentalOption("prefs", prefs);
                String downloadFilepath = ".\\src\\test\\resources\\downloads";
                prefs.put("profile.default_content_setting_values.notifications", 2);
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.default_directory", downloadFilepath);
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setCapability("key", System.getenv("key"));
                capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
                browserName = System.getenv("browser");
//                driver = new RemoteWebDriver(new URL("http://hub.testinium.io/wd/hub"), capabilities);
                URI uri = URI.create("http://hub.testinium.io/wd/hub");
                driver = new RemoteWebDriver(uri.toURL(), capabilities);
                actions = new Actions(driver);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        wait = new WebDriverWait(driver, 10);
    }

    @AfterScenario
    public void tearDown() {
        driver.quit();
    }

    public void initMap(File[] fileList) {
        Type elementType = new TypeToken<List<ElementInfo>>() {
        }.getType();
        Gson gson = new Gson();
        List<ElementInfo> elementInfoList = null;
        for (File file : fileList) {
            try {
                elementInfoList = gson
                        .fromJson(new FileReader(file), elementType);
                elementInfoList.parallelStream()
                        .forEach(elementInfo -> elementMapList.put(elementInfo.getKey(), elementInfo));
            } catch (FileNotFoundException e) {
                logger.warn("{} not found", e);
            }
        }
    }

    public File[] getFileList() {
        File[] fileList = new File(
                this.getClass().getClassLoader().getResource(DEFAULT_DIRECTORY_PATH).getFile())
                .listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));

        if (fileList == null) {
            logger.warn(
                    "File Directory Is Not Found! Please Check Directory Location. Default Directory Path = {}",
                    DEFAULT_DIRECTORY_PATH);
            throw new NullPointerException();
        }

        return fileList;
    }

    /**
     * Set Chrome options
     *
     * @return the chrome options
     */
    public ChromeOptions chromeOptions() {
        chromeOptions = new ChromeOptions();
        LoggingPreferences loggingprefs = new LoggingPreferences();
        loggingprefs.enable(LogType.BROWSER, Level.ALL);
        loggingprefs.enable(LogType.CLIENT, Level.ALL);
        loggingprefs.enable(LogType.PERFORMANCE, Level.ALL);
        loggingprefs.enable(LogType.PROFILER, Level.ALL);
        capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        chromeOptions.setCapability("goog:loggingPrefs", loggingprefs);
        chromeOptions.setExperimentalOption("prefs", prefs);
        String downloadFilepath = "./desktop";
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", downloadFilepath);
        //chromeOptions.addArguments("--kiosk");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--start-fullscreen");
//         chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//         chromeOptions.setExperimentalOption("useAutomationExtension", false);
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/chromedriver.exe");

        chromeOptions.merge(capabilities);
        return chromeOptions;

    }

    /**
     * Set Firefox options
     *
     * @return the firefox options
     */
    public FirefoxOptions firefoxOptions() {
        firefoxOptions = new FirefoxOptions();
        capabilities = DesiredCapabilities.firefox();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        firefoxOptions.addArguments("--kiosk");
        firefoxOptions.addArguments("--disable-notifications");
        firefoxOptions.addArguments("--start-fullscreen");
        FirefoxProfile profile = new FirefoxProfile();
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        capabilities.setCapability("marionette", true);
        firefoxOptions.merge(capabilities);
        System.setProperty("webdriver.gecko.driver", "web_driver/geckodriver");
        return firefoxOptions;
    }

    public ElementInfo findElementInfoByKey(String key) {
        return (ElementInfo) elementMapList.get(key);
    }

    public void saveValue(String key, String value) {
        elementMapList.put(key, value);
    }

    public String getValue(String key) {
        return elementMapList.get(key).toString();
    }


}
