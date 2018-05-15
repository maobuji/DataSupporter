package www.lianjia.com.sz.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import www.lianjia.com.domian.CommunityInfo;
import www.lianjia.com.repository.CommunityInfoRepository;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */
@Component
public class QueryDataTask implements ApplicationContextAware, InitializingBean, DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(QueryDataTask.class);

    private ApplicationContext applicationContext;

    @Autowired
    private CommunityInfoRepository communityInfoRepository;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        System.setProperty("webdriver.chrome.driver", "tool-repository/selenium/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        fillPetInfo(driver,"恒星园");

    }


    private static void fillPetInfo(WebDriver driver, String name) {
        String url = "https://sz.lianjia.com/ershoufang/rs"+name+"/";
        driver.get(url);

        try {
            Thread.sleep(100);
        } catch (Exception ex) {

        }

        List<WebElement> webElements = driver.findElements(By.xpath("/html/body/div[4]/div[1]/ul/li"));
        System.out.println(webElements.size());
        List<WebElement> webElements1 = driver.findElements(By.xpath("//*[@id=\"lessResultIds\"]/div/ul/li"));
        System.out.println(webElements1.size());







    }

    @Override
    public void destroy() throws Exception {

    }
}
