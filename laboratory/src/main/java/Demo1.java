
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import petchain.baidu.com.domain.PetInfo;
import petchain.baidu.com.util.PetchainIDUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class Demo1 {

    private static Logger logger = LoggerFactory.getLogger(Demo1.class);

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
        for (int i = 0; i < 1000; i++) {
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
                logger.info(petInfo.getAttList());
                if (petInfo.getPrice() != null) {
                    logger.info("价格:" + petInfo.getPrice());
                }
                logger.info("----------------------------------------");


            }

        }
        driver.quit();
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
                        logger.error("对应的属性没有找到："+singleAtt[0]);
                        break;
                }
            }


        } catch (NoSuchElementException e) {
            return petInfo;
        } catch (Exception ex) {
            logger.error("没有找到宠物所有者,ID=" + ID, ex);
            return petInfo;
        }


        return petInfo;
    }


}