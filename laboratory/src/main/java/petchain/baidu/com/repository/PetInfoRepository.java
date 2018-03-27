package petchain.baidu.com.repository;

import org.springframework.data.repository.CrudRepository;
import petchain.baidu.com.domain.PetInfo;

/**
 * Created by Administrator on 2018/3/27.
 */
public interface  PetInfoRepository extends CrudRepository<PetInfo, Long> {

}
