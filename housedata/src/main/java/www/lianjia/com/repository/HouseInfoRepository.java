package www.lianjia.com.repository;

import org.springframework.data.repository.CrudRepository;
import www.lianjia.com.domian.CommunityInfo;
import www.lianjia.com.domian.HouseInfo;

/**
 * Created by Administrator on 2018/5/18.
 */
public interface HouseInfoRepository extends CrudRepository<HouseInfo, String> {

    public HouseInfo findByUrl(String url);
}
