
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;

public class Demo1 {
    /**
     * @param args
     */
    public static void main(String[] args) {

        // TODO Auto-generated method stub  
        //如果测试的浏览器没有安装在默认目录，那么必须在程序中设置   
        //bug1:System.setProperty("webdriver.chrome.driver", "C://Program Files (x86)//Google//Chrome//Application//chrome.exe");  
        //bug2:System.setProperty("webdriver.chrome.driver", "C://Users//Yoga//Downloads//chromedriver_win32//chromedriver.exe");  
        System.setProperty("webdriver.chrome.driver", "tool-repository/selenium/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // "https://pet-chain.baidu.com/chain/detail?channel=market&petId=1982448327069267353&validCode=&appId=1&tpl="
        long number=1982448327069267353L;
        for(int i=0;i<1000;i++) {

            try {
                driver.get("https://pet-chain.baidu.com/chain/detail?channel=market&petId=" + (number++) + "&validCode=&appId=1&tpl=");
                WebElement webElement = driver.findElement(By.ByClassName.className("price"));

                Thread.sleep(10);
                System.out.println(webElement.getText());
            }catch (Exception ex){

            }
        }
        driver.quit();
    }
}  