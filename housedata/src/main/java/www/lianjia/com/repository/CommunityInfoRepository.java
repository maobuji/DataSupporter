package www.lianjia.com.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import www.lianjia.com.domian.CommunityInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */
public interface CommunityInfoRepository extends CrudRepository<CommunityInfo,String> {

//    @Query("select p from CommunityInfo p where name=?1")
//    public CommunityInfo queryByName(String name);

    public CommunityInfo findByName(String name);

    @Query(value = "select c from CommunityInfo c where forceFlush<>0")
    List<CommunityInfo> findNeedFlush();

}
