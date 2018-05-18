package www.lianjia.com.domian;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    // 链接地址
    @Column(length = 100)
    private String url;

    // 说明
    @Column(length = 100)
    private String sellTitle;

    // 总价
    private int price;

    // 单价
    private int unitPrice;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}
