
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

        String rareDegree="rareDegree"; // 稀有度
        String generation="generation"; // 第几代
        String coolingInterval="coolingInterval"; // 繁育时间
        String name="name"; // 狗编号
        String price="price"; // 价格


        // TODO Auto-generated method stub  
        //如果测试的浏览器没有安装在默认目录，那么必须在程序中设置   
        //bug1:System.setProperty("webdriver.chrome.driver", "C://Program Files (x86)//Google//Chrome//Application//chrome.exe");  
        //bug2:System.setProperty("webdriver.chrome.driver", "C://Users//Yoga//Downloads//chromedriver_win32//chromedriver.exe");  
        System.setProperty("webdriver.chrome.driver", "tool-repository/selenium/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        long number=1952865279528009022L;
        for(int i=0;i<1000;i++) {
            String url="https://pet-chain.baidu.com/chain/detail?channel=market&petId=" + (number++) + "&validCode=&appId=1&tpl=";
            try {
                driver.get(url);
                WebElement webElement = null;

                webElement = driver.findElement(By.ByClassName.className(rareDegree));
                System.out.println(webElement.getText());



                webElement = driver.findElement(By.ByClassName.className(generation));
                System.out.println(webElement.getText());

                webElement = driver.findElement(By.ByClassName.className(coolingInterval));
                System.out.println(webElement.getText());

                webElement = driver.findElement(By.ByClassName.className(name));
                System.out.println(webElement.getText());

                webElement = driver.findElement(By.ByClassName.className(price));
                System.out.println(webElement.getText());

                System.out.println("_______________________________");

            }catch (Exception ex){

                //ex.printStackTrace();

            }
        }
        driver.quit();
    }
}  