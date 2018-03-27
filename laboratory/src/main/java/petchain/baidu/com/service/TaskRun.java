package petchain.baidu.com.service;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
import petchain.baidu.com.domain.PetInfo;
import petchain.baidu.com.repository.PetInfoRepository;
import petchain.baidu.com.util.PetchainIDUtil;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/3/27.
 */
@Component
public class TaskRun implements ApplicationContextAware, InitializingBean, DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(TaskRun.class);

    private ApplicationContext applicationContext;

    @Autowired
    private PetInfoRepository petInfoRepository;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        System.setProperty("webdriver.chrome.driver", "tool-repository/selenium/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        while (true) {
            long ID = PetchainIDUtil.getID();
            PetInfo petInfo = fillPetInfo(driver, ID);

            if (petInfo.getName() != null) {
                logger.info("----------------------------------------");
                logger.info("ID:" + petInfo.getId());
                logger.info("名字:" + petInfo.getName());
                logger.info("稀有度:" + petInfo.getRareDegree());
                logger.info("代数：" + petInfo.getGeneration());
                logger.info("冷却时间：" + petInfo.getCoolingInterval());
                logger.info("所有者：" + petInfo.getOwner());
                logger.info("繁育：" + petInfo.getFanyuState());
                logger.info(petInfo.getAttList());
                if (petInfo.getPrice() != null) {
                    logger.info("价格:" + petInfo.getPrice());
                }
                petInfoRepository.save(petInfo);
            }else{
                logger.info("ID="+ID);
            }
        }
}

    private static PetInfo fillPetInfo(WebDriver driver, long ID) {
        String url = "https://pet-chain.baidu.com/chain/detail?channel=market&petId=" + ID + "&validCode=&appId=1&tpl=";
        driver.get(url);

        try {
            Thread.sleep(100);
        } catch (Exception ex) {

        }


        PetInfo petInfo = new PetInfo();
        petInfo.setId(ID);

        try {
            WebElement webElement = driver.findElement(By.ByClassName.className("name"));
            petInfo.setName(webElement.getText());
        } catch (NoSuchElementException e) {
            return petInfo;
        } catch (Exception ex) {
            logger.error("没有找到宠物名字,ID=" + ID, ex);
            return petInfo;
        }

        try {
//          WebElement webElement = driver.findElements(By.ByClassName.className("price"));
            WebElement webElement = driver.findElement(By.xpath("/html/body/div/section/div[2]/span[1]"));
            String sPrice=webElement.getText();
            petInfo.setPrice(new BigDecimal(sPrice.replaceAll("微","")));
        } catch (NoSuchElementException e) {

        } catch (Exception ex) {
            logger.error("没有找到宠物价格,ID=" + ID, ex);
            return petInfo;
        }

        try {
            WebElement webElement = driver.findElement(By.xpath("/html/body/div/section/div[2]/span[2]"));
            petInfo.setFanyuState(webElement.getText());
        } catch (NoSuchElementException e) {

        } catch (Exception ex) {
            logger.error("没有找到宠物繁育状态,ID=" + ID, ex);
            return petInfo;
        }


        try {
//          WebElement webElement = driver.findElements(By.ByClassName.className("price"));
            WebElement webElement = driver.findElement(By.xpath("/html/body/div/section/div[2]/span[1]"));
            String sPrice=webElement.getText();
            petInfo.setPrice(new BigDecimal(sPrice.replaceAll("微","")));
        } catch (NoSuchElementException e) {

        } catch (Exception ex) {
            logger.error("没有找到宠物价格,ID=" + ID, ex);
            return petInfo;
        }

        try {
            WebElement webElement = driver.findElement(By.ByClassName.className("rareDegree"));
            petInfo.setRareDegree(webElement.getText());
        } catch (NoSuchElementException e) {
            return petInfo;
        } catch (Exception ex) {
            logger.error("没有找到宠物稀有度,ID=" + ID, ex);
            return petInfo;
        }

        try {
            WebElement webElement = driver.findElement(By.ByClassName.className("generation"));
            petInfo.setGeneration(webElement.getText());
        } catch (NoSuchElementException e) {
            return petInfo;
        } catch (Exception ex) {
            logger.error("没有找到宠物代数,ID=" + ID, ex);
            return petInfo;
        }

        try {
            WebElement webElement = driver.findElement(By.ByClassName.className("coolingInterval"));
            petInfo.setCoolingInterval(webElement.getText());
        } catch (NoSuchElementException e) {
            return petInfo;
        } catch (Exception ex) {
            logger.error("没有找到宠物冷却时间,ID=" + ID, ex);
            return petInfo;
        }

        try {
            WebElement webElement = driver.findElement(By.ByClassName.className("f16"));
            petInfo.setOwner(webElement.getText());
        } catch (NoSuchElementException e) {
            return petInfo;
        } catch (Exception ex) {
            logger.error("没有找到宠物所有者,ID=" + ID, ex);
            return petInfo;
        }

        try {
            WebElement webElement = driver.findElement(By.ByClassName.className("list"));
            String list = webElement.getText();
            String[] atts = list.split(" ");
            for (String att : atts) {
                String[] singleAtt = att.split("：");
                switch (singleAtt[0]) {
                    case "体型":
                        petInfo.setAttTiXing(singleAtt[1]);
                        break;
                    case "花纹":
                        petInfo.setAttHuaWen(singleAtt[1]);
                        break;
                    case "眼睛":
                        petInfo.setAttYanJing(singleAtt[1]);
                        break;
                    case "眼睛色":
                        petInfo.setAttYanJingSe(singleAtt[1]);
                        break;
                    case "嘴巴":
                        petInfo.setAttZuiBa(singleAtt[1]);
                        break;
                    case "肚皮色":
                        petInfo.setAttDuPiSe(singleAtt[1]);
                        break;
                    case "身体色":
                        petInfo.setAttShenTiSe(singleAtt[1]);
                        break;
                    case "花纹色":
                        petInfo.setAttHuaWenSe(singleAtt[1]);
                        break;

                    default:
                        logger.error("对应的属性没有找到：" + singleAtt[0]);
                        break;
                }
            }


            url = "https://pet-chain.baidu.com/chain/detail?channel=breed&petId=" + ID + "&validCode=&appId=1&tpl=";
            driver.get(url);
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
            }

            try {
                webElement = driver.findElement(By.xpath("/html/body/div/section/div[2]/span[1]"));
                String sPrice=webElement.getText();
                petInfo.setFanyuPrice(new BigDecimal(sPrice.replaceAll("微","")));
            } catch (NoSuchElementException e) {

            } catch (Exception ex) {
                logger.error("没有找到宠物繁育价格,ID=" + ID, ex);
                return petInfo;
            }

            try {
                webElement = driver.findElement(By.xpath("/html/body/div/section/div[2]/span[2]"));
                petInfo.setFanyuState(webElement.getText());
            } catch (NoSuchElementException e) {

            } catch (Exception ex) {
                logger.error("没有找到宠物繁育状态,ID=" + ID, ex);
                return petInfo;
            }



        } catch (NoSuchElementException e) {
            return petInfo;
        } catch (Exception ex) {
            logger.error("没有找到宠物所有者,ID=" + ID, ex);
            return petInfo;
        }


        return petInfo;
    }

    @Override
    public void destroy() throws Exception {

    }
}
