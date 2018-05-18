package www.lianjia.com.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Administrator on 2018/5/18.
 */
public class WebDrivereUtil {

    private static WebDriver driver;

    public static synchronized WebDriver getWebDrivere() {
        System.setProperty("webdriver.chrome.driver", "tool-repository/selenium/chromedriver.exe");
        if (driver == null) {
            driver = new ChromeDriver();
        }
        return driver;
    }

    public static synchronized void close() {
        if (driver != null) {
            try {
                driver.close();

            } catch (Exception ex) {

            }
            driver = null;
        }
    }
}
