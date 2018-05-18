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
    @Column(length = 32)
    private String url;

    // 说明
    @Column(length = 100)
    private String sellTitle;
}
