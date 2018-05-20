package www.lianjia.com.domian;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/18.
 */
@Entity
@javax.persistence.Table(name = "t_house")
public class HouseInfo {

    // 主键编号
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    // 小区ID
    @Column(length = 32)
    private String communityId;

    // 小区名
    @Column(length = 10)
    private String communityName;

    // 链接地址
    @Column(length = 100)
    private String url;

    // 说明
    @Column(length = 100)
    private String sellTitle;

    // 总价
    private BigDecimal price;

    // 单价
    private BigDecimal unitPrice;

    // 户型
    @Column(length = 20)
    private String layout;

    // 面积
    private BigDecimal area;

    // 结构
    @Column(length = 10)
    private String struct;

    // 朝向
    @Column(length = 10)
    private String orientation;

    // 装修
    @Column(length = 8)
    private String decoration;

    // 电梯
    @Column(length = 4)
    private String elevator;

    // 梯户比例
    @Column(length = 16)
    private String elevatorRate;

    // 土地年限
    private int landYear;

    // 挂牌时间
    private Date submitDay;

    // 上次交易时间
    private Date lastSelledDay;

    // 交易权属
    @Column(length = 10)
    private String sellType;

    // 房屋用途
    @Column(length = 10)
    private String useType;

    // 产权所属
    @Column(length = 10)
    private String rightType;

    // 抵押
    @Column(length = 40)
    private String mortgage;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date flushTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSellTitle() {
        return sellTitle;
    }

    public void setSellTitle(String sellTitle) {
        this.sellTitle = sellTitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Date getFlushTime() {
        return flushTime;
    }

    public void setFlushTime(Date flushTime) {
        this.flushTime = flushTime;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getStruct() {
        return struct;
    }

    public void setStruct(String struct) {
        this.struct = struct;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getElevatorRate() {
        return elevatorRate;
    }

    public void setElevatorRate(String elevatorRate) {
        this.elevatorRate = elevatorRate;
    }

    public int getLandYear() {
        return landYear;
    }

    public void setLandYear(int landYear) {
        this.landYear = landYear;
    }

    public Date getSubmitDay() {
        return submitDay;
    }

    public void setSubmitDay(Date submitDay) {
        this.submitDay = submitDay;
    }

    public Date getLastSelledDay() {
        return lastSelledDay;
    }

    public void setLastSelledDay(Date lastSelledDay) {
        this.lastSelledDay = lastSelledDay;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }
}
