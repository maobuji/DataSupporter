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
import www.lianjia.com.util.WebDrivereUtil;

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

        while (true) {
            try {
                flushCommunity();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void flushCommunity() throws Exception {
        List<CommunityInfo> ls = communityInfoRepository.findNeedFlush();
        for (CommunityInfo communityInfo : ls) {
            fillPetInfo(communityInfo);
            communityInfo.setForceFlush(0);
            communityInfoRepository.save(communityInfo);
        }

        if (ls.size() == 0) {
            Thread.sleep(3000);
        }
    }


    private void fillPetInfo(CommunityInfo communityInfo) {
        String url = "https://sz.lianjia.com/ershoufang/rs" + communityInfo.getName() + "/";
        WebDriver driver = WebDrivereUtil.getWebDrivere();
        driver.get(url);
        try {
            Thread.sleep(100);
        } catch (Exception ex) {

        }
        // 获取小区总体说明
//*[@id="sem_card"]/div/div[2]/div[1]/a[1]
        // 小区名
        String name = driver.findElement(By.className("agentCardResblockTitle")).getText();
        if (!communityInfo.getName().equalsIgnoreCase(name)) {

            // 小区明细信息的url
            String comUrl = driver.findElement(By.className("agentCardResblockTitle")).getAttribute("href");
            // 小区平均价格
            String averagePriceTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[1]/a")).getText();

            // 在售数量
            String onSellCount = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[2]/div[2]")).getText();

            // 90天成交
            String day90Selled = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[3]/a")).getText();

            // 近30天带看
            String day30look = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[4]/div[2]")).getText();
        }


        // 在售列表
        List<WebElement> communityOnSells = driver.findElements(By.xpath("/html/body/div[4]/div[1]/ul/li"));
        System.out.println("在售条数：" + communityOnSells.size());
        for (WebElement singleOnSellElement : communityOnSells) {

            // 描述信息：恒星园4房，一手业主红本手 观小区花园 视野开阔
            try {
                String title = singleOnSellElement.findElement(By.xpath("div[1]/div[1]/a")).getText();
                System.out.println(title);
            }catch (Exception ex){
                ex.printStackTrace();
                continue;
            }

            // 链接地址：https://sz.lianjia.com/ershoufang/105100063278.html
            singleOnSellElement.findElement(By.xpath("div[1]/div[1]/a")).getAttribute("href");
            try {
                // 房主自鉴：可能有
                singleOnSellElement.findElement(By.xpath("div[1]/div[1]/span")).getText();
            } catch (Exception ex) {

            }
            // 房产tag ：恒星园 | 4室2厅 | 129.86平米 | 西南 | 精装 | 有电梯
            singleOnSellElement.findElement(By.xpath("div[1]/div[2]/div")).getText();
            // 房产类型：低楼层(共18层)1999年建板塔结合 - 香蜜湖
            singleOnSellElement.findElement(By.xpath("div[1]/div[3]/div")).getText();
            // 房产信息：99人关注 / 共30次带看 / 一年前发布
            singleOnSellElement.findElement(By.xpath("div[1]/div[4]")).getText();

            // 特点：距离7号线农林站313米 房本满五年
            List<WebElement> tedianWebElements = singleOnSellElement.findElements(By.xpath("div[1]/div[5]/span"));
            for (WebElement tedianWebElement : tedianWebElements) {

            }

            // 价格（万）：1000
            singleOnSellElement.findElement(By.xpath("div[1]/div[6]/div[1]/span")).getText();
            // 单价
            singleOnSellElement.findElement(By.xpath("div[1]/div[6]/div[2]/span")).getText();
        }

        // 推荐列表
        List<WebElement> communityRecommends = driver.findElements(By.xpath("//*[@id=\"lessResultIds\"]/div/ul/li"));
        System.out.println("推荐条数：" + communityRecommends.size());
        for (WebElement recommendElement : communityRecommends) {
            // 描述信息：恒星园4房，一手业主红本手 观小区花园 视野开阔
            String title = recommendElement.findElement(By.xpath("div[1]/div[1]/a")).getText();
            System.out.println(title);

            String tags = recommendElement.findElement(By.xpath("div[1]/div[2]/div")).getText();
            String[] tag = tags.split("/");
            String cName = tag[0].trim();

            CommunityInfo newCommunityInfo = communityInfoRepository.findByName(cName);
            if (newCommunityInfo == null) {
                newCommunityInfo = new CommunityInfo();
                newCommunityInfo.setName(cName);
                newCommunityInfo.setForceFlush(3);
                communityInfoRepository.save(newCommunityInfo);
            }
        }
    }

    @Override
    public void destroy() throws Exception {

    }
}
