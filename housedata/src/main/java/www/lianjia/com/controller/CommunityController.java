package www.lianjia.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import www.lianjia.com.domian.CommunityInfo;
import www.lianjia.com.repository.CommunityInfoRepository;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */
@RestController
public class CommunityController {

    @Autowired
    private CommunityInfoRepository communityInfoRepository;

    @RequestMapping(value = "/flush", method = RequestMethod.GET)
    public String add(@RequestParam String name, int flush) {

        String result="";
        CommunityInfo communityInfo = communityInfoRepository.findByName(name);
        if (communityInfo == null) {
            communityInfo = new CommunityInfo();
            result="新增成功";
        }else{
            result="刷新成功";
        }
        communityInfo.setName(name);
        communityInfo.setForceFlush(flush);
        communityInfoRepository.save(communityInfo);
        return result;
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String start() {
        String name="恒星园";
        int flush =2;

        String result="";
        CommunityInfo communityInfo = communityInfoRepository.findByName(name);
        if (communityInfo == null) {
            communityInfo = new CommunityInfo();
            result="新增成功";
        }else{
            result="刷新成功";
        }
        communityInfo.setName(name);
        communityInfo.setForceFlush(flush);
        communityInfoRepository.save(communityInfo);
        return result;
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {

        List<CommunityInfo> ls= communityInfoRepository.findNeedFlush();
        System.out.println(ls.size());

    }


}
