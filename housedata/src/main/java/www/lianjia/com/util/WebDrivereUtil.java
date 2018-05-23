package www.lianjia.com.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/5/18.
 */
public class WebDrivereUtil {



    public static synchronized WebDriver getWebDrivere() {
        System.setProperty("webdriver.chrome.driver", "tool-repository/selenium/chromedriver.exe");

        HashMap<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.images", 2);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(chromeOptions);
    }

    public static synchronized void close(WebDriver driver) {
        if (driver != null) {
            try {
                driver.close();
            } catch (Exception ex) {
            }
            driver = null;
        }
    }
}
