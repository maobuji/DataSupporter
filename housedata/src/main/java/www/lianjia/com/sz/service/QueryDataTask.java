package www.lianjia.com.sz.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import www.lianjia.com.domian.HouseInfo;
import www.lianjia.com.repository.CommunityInfoRepository;
import www.lianjia.com.repository.HouseInfoRepository;
import www.lianjia.com.util.WebDrivereUtil;

import java.util.Date;
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

    @Autowired
    private HouseInfoRepository houseInfoRepository;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        flushCommunity();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(true);
        t.setName("cycflushThread");
        t.start();
    }

    public void flushCommunity() throws Exception {
        List<CommunityInfo> ls = communityInfoRepository.findNeedFlush();
        for (CommunityInfo communityInfo : ls) {
            flushCommunityInfo(communityInfo);
            communityInfo.setForceFlush(0);
            communityInfo.setFlushTime(new Date());
            communityInfoRepository.save(communityInfo);
        }

        if (ls.size() == 0) {
            Thread.sleep(3000);
        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception ex) {

        }
    }


    private void flushCommunityInfo(CommunityInfo communityInfo) {
        String url = "https://sz.lianjia.com/ershoufang/rs" + communityInfo.getName() + "/";
        WebDriver driver = WebDrivereUtil.getWebDrivere();
        driver.get(url);
        sleep(100);


        // 获取小区总体说明

        // 小区名
        String name = driver.findElement(By.className("agentCardResblockTitle")).getText();
        if (!communityInfo.getName().equalsIgnoreCase(name)) {
            return;
        }

        // 获取小区的一般信息
        flushGeneralInfo(communityInfo, driver);
        System.out.println("小区：" + communityInfo.getName() + " 在售数量" + communityInfo.getOnSellCount() + "**************");


        if (communityInfo.getForceFlush() != 0 && communityInfo.getForceFlush() != 1) {
            // 获取小区的销售明细
            flushSellInfo(communityInfo, driver);
        }

        // 获取推荐小区
        flushRecommendInfo(communityInfo, driver);

        flushGeneralDetail(communityInfo, driver);
    }

    private void flushGeneralDetail(CommunityInfo communityInfo, WebDriver driver) {
        if (communityInfo.getBuildType() != null && !communityInfo.equals("")) {
            return;
        }
        driver.get(communityInfo.getUrl());
        sleep(100);

        // 建筑年代:2007年建成
        String buildYearTemp = driver.findElement(By.xpath("/html/body/div[6]/div[2]/div[2]/div[1]/span[2]")).getText();
        buildYearTemp = buildYearTemp.replaceAll("年建成", "");
        communityInfo.setBuildYear(Integer.valueOf(transNull2Zero(buildYearTemp)));

        // 建筑类型：板楼
        String buildTypeTemp = driver.findElement(By.xpath("/html/body/div[6]/div[2]/div[2]/div[2]/span[2]")).getText();
        communityInfo.setBuildType(buildTypeTemp);

        // 开发商名称
        String developer = driver.findElement(By.xpath("/html/body/div[6]/div[2]/div[2]/div[5]/span[2]")).getText();
        communityInfo.setDeveloper(developer);

        // 楼栋总数:11栋
        String buildingCount = driver.findElement(By.xpath("/html/body/div[6]/div[2]/div[2]/div[6]/span[2]")).getText();
        buildingCount = buildingCount.replaceAll("栋", "");
        communityInfo.setBuildingCount(Integer.valueOf(transNull2Zero(buildingCount)));

        // 房屋总数:1339户
        String houseCount = driver.findElement(By.xpath("/html/body/div[6]/div[2]/div[2]/div[7]/span[2]")).getText();
        houseCount = houseCount.replaceAll("户", "");
        communityInfo.setHouseCount(Integer.valueOf(transNull2Zero(houseCount)));
    }

    private void flushRecommendInfo(CommunityInfo communityInfo, WebDriver driver) {
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

            // 如果小区不存在，则将小区加入到查询列表中
            CommunityInfo newCommunityInfo = communityInfoRepository.findByName(cName);
            if (newCommunityInfo == null) {
                newCommunityInfo = new CommunityInfo();
                newCommunityInfo.setName(cName);
                newCommunityInfo.setForceFlush(3);
                communityInfoRepository.save(newCommunityInfo);
            }
        }
    }

    private void flushSellInfo(CommunityInfo communityInfo, WebDriver driver) {
        // 在售列表
        List<WebElement> communityOnSells = driver.findElements(By.xpath("/html/body/div[4]/div[1]/ul/li"));
        for (WebElement singleOnSellElement : communityOnSells) {
            // 链接地址：https://sz.lianjia.com/ershoufang/105100063278.html
            HouseInfo houseInfo = null;
            String url = "";
            try {
                url = singleOnSellElement.findElement(By.xpath("div[1]/div[1]/a")).getAttribute("href");
                houseInfo = houseInfoRepository.findByUrl(url);
            } catch (Exception ex) {
                continue;
            }

            if (houseInfo == null) {
                houseInfo = new HouseInfo();
                houseInfo.setUrl(url);
            } else {
                continue;
            }


            // 描述信息：恒星园4房，一手业主红本手 观小区花园 视野开阔
            try {
                String sellTitle = singleOnSellElement.findElement(By.xpath("div[1]/div[1]/a")).getText();
                houseInfo.setSellTitle(sellTitle);
                System.out.println(sellTitle);
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }


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
            String price = singleOnSellElement.findElement(By.xpath("div[1]/div[6]/div[1]/span")).getText();
            houseInfo.setPrice(Integer.valueOf(price));
            // 单价
            String unitPrice = singleOnSellElement.findElement(By.xpath("div[1]/div[6]/div[2]/span")).getText();
            unitPrice = unitPrice.replaceAll("元/平米", "").replaceAll("单价", "");
            houseInfo.setUnitPrice(Integer.valueOf(unitPrice));

            houseInfo.setCommunityId(communityInfo.getId());
            houseInfoRepository.save(houseInfo);
        }
    }

    private void flushGeneralInfo(CommunityInfo communityInfo, WebDriver driver) {
        // 小区明细信息的url：https://sz.lianjia.com/xiaoqu/2411048917573/
        String urlTemp = driver.findElement(By.className("agentCardResblockTitle")).getAttribute("href");
        communityInfo.setUrl(urlTemp);

        // 小区平均价格:121746元/平米
        String averagePriceTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[1]/a")).getText();
        communityInfo.setAveragePrice(Integer.valueOf(averagePriceTemp.replaceAll("元/平米", "")));

        // 在售数量:23套
        String onSellCountTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[2]/div[2]")).getText();
        onSellCountTemp = onSellCountTemp.replaceAll("套", "");
        communityInfo.setOnSellCount(Integer.valueOf(transNull2Zero(onSellCountTemp)));

        // 90天成交:5套
        String day90SelledTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[3]/a")).getText();
        day90SelledTemp = day90SelledTemp.replaceAll("套", "");
        communityInfo.setDay90Selled(Integer.valueOf(transNull2Zero(day90SelledTemp)));

        // 近30天带看:189次
        String day30lookTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[4]/div[2]")).getText();
        day30lookTemp = day30lookTemp.replaceAll("次", "");
        communityInfo.setDay30look(Integer.valueOf(transNull2Zero(day30lookTemp)));
    }

    public static String transNull2Zero(String s) {
        s = s.trim();
        if (s.equals("")) {
            s = "0";
        }
        return s;
    }

    @Override
    public void destroy() throws Exception {

    }
}
