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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import www.lianjia.com.domian.CommunityInfo;
import www.lianjia.com.domian.HouseInfo;
import www.lianjia.com.repository.CommunityInfoRepository;
import www.lianjia.com.repository.HouseInfoRepository;
import www.lianjia.com.util.WebDrivereUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        final int threadCount=5;
        for(int i=1;i<threadCount+1;i++) {
            final int number=i;
            WebDriver driver = WebDrivereUtil.getWebDrivere();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {

                            flushCommunity(number,threadCount,driver);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            t.setDaemon(true);
            t.setName("QueryDataTaskThread"+i);
            t.start();
        }
    }

    public void flushCommunity(int number,int threadCount,WebDriver driver) throws Exception {
        List<CommunityInfo> ls = communityInfoRepository.findNeedFlush(PageRequest.of(0,50));
        for (CommunityInfo communityInfo : ls) {
            String uuid=communityInfo.getId();
            int myInt=Integer.valueOf(uuid.charAt(uuid.length()-1)).intValue();
            if(myInt%threadCount!=(number-1)){
                continue;
            }
            System.out.println("Thread"+number+":"+communityInfo.getName());
            flushCommunityInfo(communityInfo,driver);
            communityInfo.setForceFlush(0);
            communityInfoRepository.save(communityInfo);
        }

        if (ls.size() == 0) {
            Thread.sleep(5000);
        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception ex) {

        }
    }

    private static long DRIVER_GET_WAIT_TIME = 500;


    private void flushCommunityInfo(CommunityInfo communityInfo,WebDriver driver) {

        communityInfo.setCity("深圳");
        communityInfo.setFlushTime(new Date());

        // 获取小区详细信息
        flushGeneralDetail(communityInfo, driver);

        int houseCount=0;

        // 获取小区总体说明
        for(int i=1;i<20;i++){
            String url="https://sz.lianjia.com/ershoufang/pg"+i+"rs"+communityInfo.getName() + "/";
            driver.get(url);
            sleep(DRIVER_GET_WAIT_TIME);

            if(i==1){
                // 获取小区的一般信息
                flushGeneralInfo(communityInfo, driver);
                System.out.println("小区：" + communityInfo.getName() + " 在售数量" + communityInfo.getOnSellCount() + "**************");
            }

            // 获取推荐小区
            flushRecommendInfo(communityInfo, driver);

            // 获取小区在售房源
            int sellCount=flushSellInfo(communityInfo, driver);
            houseCount=houseCount+sellCount;
            if(sellCount<30){
                break;
            }
        }

        communityInfo.setCollectCount(houseCount);

    }

    private void flushGeneralDetail(CommunityInfo communityInfo, WebDriver driver) {
        if (communityInfo.getBuildType() != null && !communityInfo.getDeveloper().equals("")) {
            return;
        }
        driver.get(communityInfo.getUrl());
        sleep(DRIVER_GET_WAIT_TIME);

        String address = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div")).getText();
        String disrict = address.substring(1, address.indexOf("区") + 1);
        communityInfo.setDisrict(disrict);
        String section = address.substring(address.indexOf("区") + 1, address.indexOf(")"));
        communityInfo.setSection(section);
        String site = address.substring(address.indexOf(")") + 1, address.length());
        communityInfo.setSite(site);


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
        System.out.println("****************推荐条数：" + communityRecommends.size());

        for (WebElement recommendElement : communityRecommends) {
            // 描述信息：恒星园4房，一手业主红本手 观小区花园 视野开阔
            String title = recommendElement.findElement(By.xpath("div[1]/div[1]/a")).getText();
            System.out.println(title);

            // 分解出小区名
            String tags = recommendElement.findElement(By.xpath("div[1]/div[2]/div")).getText();
            String[] tag = tags.split("/");
            String cName = tag[0].trim();

            // TODO url
            communityAddCheck(cName,"");
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



    @Transactional
    private int flushSellInfo(CommunityInfo communityInfo, WebDriver driver) {
        // 在售列表
        List<WebElement> communityOnSells = driver.findElements(By.xpath("/html/body/div[4]/div[1]/ul/li"));
        System.out.println("****************在售条数：" + communityOnSells.size());

        List<HouseInfo> waitFlushHouseInfos = new ArrayList<HouseInfo>();

        for (WebElement singleOnSellElement : communityOnSells) {
            // 链接地址：https://sz.lianjia.com/ershoufang/105100063278.html
            HouseInfo houseInfo = null;
            String url = "";

            try {
                String name = singleOnSellElement.findElement(By.xpath("div[1]/div[2]/div/a")).getText();
                String comUrl=singleOnSellElement.findElement(By.xpath("div[1]/div[2]/div/a")).getAttribute("href");
                if (!communityInfo.getName().equals(name)) {
                    // 如果小区不存在，则将小区加入到查询列表中
                    communityAddCheck(name,comUrl);
                    continue;
                }

                url = singleOnSellElement.findElement(By.xpath("div[1]/div[1]/a")).getAttribute("href");
                houseInfo = houseInfoRepository.findByUrl(url);
            } catch (Exception ex) {
                continue;
            }

            if (houseInfo == null) {
                houseInfo = new HouseInfo();
                houseInfo.setCommunityName(communityInfo.getName());
                houseInfo.setFlushTime(communityInfo.getFlushTime());
                houseInfo.setUrl(url);
            }
            waitFlushHouseInfos.add(houseInfo);

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

            // 如果小区链接没有拿到，在这里再尝试拿一次
            if (communityInfo.getUrl().equals("")) {
                if (communityInfo.getName().equals(singleOnSellElement.findElement(By.xpath("div[1]/div[2]/div/a")).getText())) {
                    communityInfo.setUrl(singleOnSellElement.findElement(By.xpath("div[1]/div[2]/div/a")).getAttribute("href"));
                }
            }

            // 价格（万）：1000
            String price = singleOnSellElement.findElement(By.xpath("div[1]/div[6]/div[1]/span")).getText();
            houseInfo.setPrice(new BigDecimal(transNull2Zero(price)));
            // 单价
            String unitPrice = singleOnSellElement.findElement(By.xpath("div[1]/div[6]/div[2]/span")).getText();
            unitPrice = unitPrice.replaceAll("元/平米", "").replaceAll("单价", "");
            houseInfo.setUnitPrice(new BigDecimal(transNull2Zero(unitPrice)));

            houseInfo.setCommunityId(communityInfo.getId());
        }

        for (HouseInfo houseInfo : waitFlushHouseInfos) {
            driver.get(houseInfo.getUrl());
            sleep(DRIVER_GET_WAIT_TIME);

            // 基本信息
            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"introduction\"]/div/div/div[1]/div[2]/ul/li"));
            for (WebElement webElement : webElements) {
                String text = webElement.getText();
                String textValue = text.substring(4, text.length()).trim();

                // 房屋类型
                if (text.startsWith("房屋户型")) {
                    houseInfo.setLayout(textValue);
                    continue;
                }

                // 户型结构：平层
                if (text.startsWith("户型结构")) {
                    houseInfo.setStruct(textValue);
                    continue;
                }

                // 建筑面积：221.07㎡
                if (text.startsWith("建筑面积")) {
                    String area = textValue.replaceAll("㎡", "").replaceAll("平米", "");
                    houseInfo.setArea(new BigDecimal(area));
                    continue;
                }

                // 房屋朝向：南
                if (text.startsWith("房屋朝向")) {
                    houseInfo.setOrientation(textValue);
                    continue;
                }

                // 套内面积：184.92㎡
                // 建筑类型：塔楼
                // 建筑结构：钢混结构

                // 装修情况：毛坯
                if (text.startsWith("装修情况")) {
                    houseInfo.setDecoration(textValue);
                    continue;
                }

                // 梯户比例：两梯四户
                if (text.startsWith("梯户比例")) {
                    houseInfo.setElevatorRate(textValue);
                    continue;
                }

                // 配备电梯：有
                if (text.startsWith("配备电梯")) {
                    houseInfo.setElevator(textValue);
                    continue;
                }

                // 产权年限：70年
                if (text.startsWith("产权年限")) {
                    try {
                        String landYear = textValue.replaceAll("年", "");
                        houseInfo.setLandYear(Integer.valueOf(landYear));
                    } catch (Exception ex) {

                    }
                    continue;
                }
            }


            // 交易信息
            webElements = driver.findElements(By.xpath("//*[@id=\"introduction\"]/div/div/div[2]/div[2]/ul/li"));
            for (WebElement webElement : webElements) {
                String text = webElement.getText();
                String textValue = text.substring(4, text.length()).trim();

                // 挂牌时间:2017-02-21
                if (text.startsWith("挂牌时间")) {
                    try {
                        if(textValue.length()==10) {
                            houseInfo.setSubmitDay(sdf.parse(textValue));
                        }
                    } catch (Exception ex) {

                    }
                    continue;
                }

                // 上次交易:2009-08-03
                if (text.startsWith("上次交易")) {
                    try {
                        if(textValue.length()==10) {
                            houseInfo.setLastSelledDay(sdf.parse(textValue));
                        }
                    } catch (Exception ex) {

                    }
                    continue;
                }

                // 交易权属:商品房
                if (text.startsWith("交易权属")) {
                    houseInfo.setSellType(textValue);
                    continue;
                }

                // 房屋用途:普通住宅
                if (text.startsWith("房屋用途")) {
                    houseInfo.setUseType(textValue);
                    continue;
                }

                // 产权所属:非共有
                if (text.startsWith("产权所属")) {
                    houseInfo.setRightType(textValue);
                    continue;
                }

                // 抵押信息:有抵押
                if (text.startsWith("抵押信息")) {
                    houseInfo.setMortgage(textValue);
                    continue;
                }

                // 房屋年限:满五年
                // 房本备件:已上传房本照片
                // 房源编码:170343231614
            }
        }
        houseInfoRepository.saveAll(waitFlushHouseInfos);
        return waitFlushHouseInfos.size();
    }

    private void flushGeneralInfo(CommunityInfo communityInfo, WebDriver driver) {

        try {
            String name = driver.findElement(By.className("agentCardResblockTitle")).getText();
            if (!communityInfo.getName().equalsIgnoreCase(name)) {
                return;
            }
        }catch (Exception ex){
            return;
        }
        // 小区平均价格:121746元/平米
        String averagePriceTemp;
        try {
            averagePriceTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[1]/a")).getText();
        } catch (Exception ex) {
            averagePriceTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[1]/span")).getText();
        }
        averagePriceTemp = averagePriceTemp.replaceAll("元/平米", "");
        communityInfo.setAveragePrice(new BigDecimal(transNull2Zero(averagePriceTemp)));

        // 在售数量:23套
        String onSellCountTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[2]/div[2]")).getText();
        onSellCountTemp = onSellCountTemp.replaceAll("套", "");
        communityInfo.setOnSellCount(Integer.valueOf(transNull2Zero(onSellCountTemp)));

        // 90天成交:5套
        String day90SelledTemp;
        try {
            day90SelledTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[3]/a")).getText();
        } catch (Exception ex) {
            day90SelledTemp = driver.findElement(By.xpath("//*[@id=\"sem_card\"]/div/div[2]/div[2]/div[3]/span")).getText();
        }
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

    private void communityAddCheck(String name,String url){
        CommunityInfo newCommunityInfo = communityInfoRepository.findByName(name);
        if (newCommunityInfo == null) {
            newCommunityInfo = new CommunityInfo();
            newCommunityInfo.setName(name);
            newCommunityInfo.setName(url);
            newCommunityInfo.setForceFlush(1);
            communityInfoRepository.save(newCommunityInfo);
        }
    }

    @Override
    public void destroy() throws Exception {

    }
}
