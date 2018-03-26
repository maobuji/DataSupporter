package petchain.baidu.com.domain;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/3/26.
 */
public class PetInfo {

    // 主键编号
    private long id;

    // 名称（狗编号）
    private String name;

    // 稀有度
    private String rareDegree;

    // 代数
    private String generation;

    // 休息时间
    private String coolingInterval;

    // 价格
    private BigDecimal price;


    // 所有者
    private String owner;

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
    private String attTiXing;

    // 属性-花纹
    private String attHuaWen;

    // 属性-眼睛
    private String attYanJing;

    // 属性-眼睛色
    private String attYanJingSe;

    // 属性-嘴巴
    private String attZuiBa;

    // 属性-肚皮色
    private String attDuPiSe;

    // 属性-身体色
    private String attShenTiSe;

    // 属性-花纹色
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

    public String getAttList() {

        return "体型:"+this.attTiXing+" 花纹:"+this.attHuaWen+" 眼睛:"+this.attYanJing +" 眼睛色:"+this.attYanJingSe+
                " 嘴巴:"+this.attZuiBa+" 肚皮色:"+this.attDuPiSe+" 身体色:"+ this.attShenTiSe+" 花纹色:"+this.attHuaWenSe;
    }

}
