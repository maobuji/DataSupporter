package www.lianjia.com.sz.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */
//@Component
public class FindCommunityTask implements ApplicationContextAware, InitializingBean, DisposableBean {

    private ApplicationContext applicationContext;

    @Autowired
    private CommunityInfoRepository communityInfoRepository;


    @Override
    public void afterPropertiesSet() throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                initCommunityList();
            }
        });
        t.setDaemon(true);
        t.setName("FindCommunityTaskThread");
        t.start();
    }

    public void initCommunityList() {
        WebDriver driver = WebDrivereUtil.getWebDrivere();

        List<String> queryUrls = new ArrayList<String>();
        queryUrls.add("https://sz.lianjia.com/xiaoqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/luohuqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/futianqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/nanshanqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/yantianqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/baoanqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/longgangqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/longhuaqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/guangmingxinqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/pingshanqu/pg");
        queryUrls.add("https://sz.lianjia.com/xiaoqu/dapengxinqu/pg");

        int emptyCount = 0;
        for (String queryUrl : queryUrls) {
            for (int i = 0; i < 300; i++) {
                driver.get(queryUrl + i + "/");
                sleep(2000);
                String name = "";
                try {
                    List<WebElement> communityList = driver.findElements(By.xpath("/html/body/div[4]/div[1]/ul/li"));
                    if (communityList.size() == 30) {
                        emptyCount = 0;
                    } else {
                        emptyCount++;
                        if (emptyCount > 10) {
                            emptyCount=0;
                            break;
                        }
                    }
                    for (WebElement webElement : communityList) {
                        name = webElement.findElement(By.xpath("div[1]/div[1]/a")).getText();
                        String url=webElement.findElement(By.xpath("div[1]/div[1]/a")).getAttribute("href");

                        if(name.equals("侨城一号广场")){
                            continue;
                        }

                        if(name.equals("朗泓龙园大观")){
                            continue;
                        }

                        CommunityInfo communityInfo = communityInfoRepository.findByName(name);
                        if (communityInfo == null) {
                            communityInfo = new CommunityInfo();
                            communityInfo.setName(name);
                            communityInfo.setUrl(url);
                            communityInfo.setForceFlush(1);
                            communityInfoRepository.save(communityInfo);
                            System.out.println("新增小区:" + name);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("新增小区错误：" + name);
                    continue;
                }
            }
        }
        driver.close();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {

    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception ex) {

        }
    }
}
