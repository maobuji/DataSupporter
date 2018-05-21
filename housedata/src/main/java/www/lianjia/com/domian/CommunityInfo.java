package www.lianjia.com.domian;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/15.
 */
@Entity
@javax.persistence.Table(name = "t_community")
public class CommunityInfo {

    // 主键编号
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    // 小区名
    @Column(length = 10,unique=true)
    private String name;

    // 强制刷新标志，0不刷新、1刷新小区信息、2刷新小区和其下所有房屋信息
    private int forceFlush = 3;

    // 小区平均价格
    private BigDecimal averagePrice;

    // 在售数数量
    private int onSellCount;

    // 已抓取到的数量
    private int collectCount;

    // 90天成交
    private int day90Selled;

    // 30天带看
    private int day30look;

    // 建筑年代
    private int buildYear;

    // 建筑类型
    @Column(length = 20)
    private String buildType;

    // 楼栋总数
    private int buildingCount;

    // 房屋总数
    private int houseCount;

    // 开发商
    @Column(length = 100)
    private String developer;

    // 城市
    @Column(length = 10)
    private String city;

    // 区
    @Column(length = 10)
    private String disrict;

    // 片
    @Column(length = 10)
    private String section;

    // 街道门牌号
    @Column(length = 50)
    private String site;

    // 小区url
    @Column(length = 80)
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date flushTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getForceFlush() {
        return forceFlush;
    }

    public void setForceFlush(int forceFlush) {
        this.forceFlush = forceFlush;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public int getOnSellCount() {
        return onSellCount;
    }

    public void setOnSellCount(int onSellCount) {
        this.onSellCount = onSellCount;
    }

    public int getDay90Selled() {
        return day90Selled;
    }

    public void setDay90Selled(int day90Selled) {
        this.day90Selled = day90Selled;
    }

    public int getDay30look() {
        return day30look;
    }

    public void setDay30look(int day30look) {
        this.day30look = day30look;
    }

    public Date getFlushTime() {
        return flushTime;
    }

    public void setFlushTime(Date flushTime) {
        this.flushTime = flushTime;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public int getBuildingCount() {
        return buildingCount;
    }

    public void setBuildingCount(int buildingCount) {
        this.buildingCount = buildingCount;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisrict() {
        return disrict;
    }

    public void setDisrict(String disrict) {
        this.disrict = disrict;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }
}
