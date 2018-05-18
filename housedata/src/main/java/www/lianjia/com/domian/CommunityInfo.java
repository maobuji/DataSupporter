package www.lianjia.com.domian;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    @Column(length = 10)
    private String name;

    // 强制刷新标志，0不刷新、1刷新小区信息、2刷新小区和其下所有房屋信息
    public int forceFlush=3;


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




}
