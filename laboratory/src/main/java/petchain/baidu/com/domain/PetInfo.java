package petchain.baidu.com.domain;

import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/26.
 */
@Entity
@javax.persistence.Table(name = "t_petinfo")
public class PetInfo {

    // 主键编号
    @Id
    private long id;

    // 名称（狗编号）
    @Column(length = 20)
    private String name;

    // 稀有度
    @Column(length = 10)
    private String rareDegree;

    // 代数
    @Column(length = 10)
    private String generation;

    // 休息时间
    @Column(length = 10)
    private String coolingInterval;

    // 出售价格
    private BigDecimal price;

    @Column(length = 10)
    private String fanyuState;



    // 繁育价格
    private BigDecimal fanyuPrice;


    // 所有者
    @Column(length = 50)
    private String owner;

    // 获取时间
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateTime=new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRareDegree() {
        return rareDegree;
    }

    public void setRareDegree(String rareDegree) {
        this.rareDegree = rareDegree;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getCoolingInterval() {
        return coolingInterval;
    }

    public void setCoolingInterval(String coolingInterval) {
        this.coolingInterval = coolingInterval;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    // 属性-体型
    @Column(length = 20)
    private String attTiXing;

    // 属性-花纹
    @Column(length = 20)
    private String attHuaWen;

    // 属性-眼睛
    @Column(length = 20)
    private String attYanJing;

    // 属性-眼睛色
    @Column(length = 20)
    private String attYanJingSe;

    // 属性-嘴巴
    @Column(length = 20)
    private String attZuiBa;

    // 属性-肚皮色
    @Column(length = 20)
    private String attDuPiSe;

    // 属性-身体色
    @Column(length = 20)
    private String attShenTiSe;

    // 属性-花纹色
    @Column(length = 20)
    private String attHuaWenSe;

    public String getAttTiXing() {
        return attTiXing;
    }

    public void setAttTiXing(String attTiXing) {
        this.attTiXing = attTiXing;
    }

    public String getAttHuaWen() {
        return attHuaWen;
    }

    public void setAttHuaWen(String attHuaWen) {
        this.attHuaWen = attHuaWen;
    }

    public String getAttYanJing() {
        return attYanJing;
    }

    public void setAttYanJing(String attYanJing) {
        this.attYanJing = attYanJing;
    }

    public String getAttYanJingSe() {
        return attYanJingSe;
    }

    public void setAttYanJingSe(String attYanJingSe) {
        this.attYanJingSe = attYanJingSe;
    }

    public String getAttZuiBa() {
        return attZuiBa;
    }

    public void setAttZuiBa(String attZuiBa) {
        this.attZuiBa = attZuiBa;
    }

    public String getAttDuPiSe() {
        return attDuPiSe;
    }

    public void setAttDuPiSe(String attDuPiSe) {
        this.attDuPiSe = attDuPiSe;
    }

    public String getAttShenTiSe() {
        return attShenTiSe;
    }

    public void setAttShenTiSe(String attShenTiSe) {
        this.attShenTiSe = attShenTiSe;
    }

    public String getAttHuaWenSe() {
        return attHuaWenSe;
    }

    public void setAttHuaWenSe(String attHuaWenSe) {
        this.attHuaWenSe = attHuaWenSe;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFanyuState() {
        return fanyuState;
    }

    public void setFanyuState(String fanyuState) {
        this.fanyuState = fanyuState;
    }

    public BigDecimal getFanyuPrice() {
        return fanyuPrice;
    }

    public void setFanyuPrice(BigDecimal fanyuPrice) {
        this.fanyuPrice = fanyuPrice;
    }

    public String getAttList() {

        return "体型:"+this.attTiXing+" 花纹:"+this.attHuaWen+" 眼睛:"+this.attYanJing +" 眼睛色:"+this.attYanJingSe+
                " 嘴巴:"+this.attZuiBa+" 肚皮色:"+this.attDuPiSe+" 身体色:"+ this.attShenTiSe+" 花纹色:"+this.attHuaWenSe;
    }

}
