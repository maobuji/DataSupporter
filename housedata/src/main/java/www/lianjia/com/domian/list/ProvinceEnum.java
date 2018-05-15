package www.lianjia.com.domian.list;

/**
 * Created by Administrator on 2018/5/15.
 */
public enum ProvinceEnum {
    BEIJINGSHI("北京市", "11"),
    TIANJINSHI("天津市", "12"),
    GUANGDONGSHENG("广东省","44");

    //    北京市（京）:11
    //    天津市（津）:12
//    上海市（沪）:31
//    重庆市（渝）:50
//    河北省（冀）:13
//    河南省（豫）:41
//    云南省（云）:53
//    辽宁省（辽）:21
//    黑龙江省（黑）:23
//
//    湖南省（湘）:43
//
//    安徽省（皖）:34
//
//    山东省（鲁）:37
//
//    新疆维吾尔（新）:65
//
//    江苏省（苏）:32
//
//    浙江省（浙）:33
//
//    江西省（赣）:36
//
//    湖北省（鄂）:42
//
//    广西壮族（桂）:45
//
//    甘肃省（甘）:62
//
//    山西省（晋）:14
//
//    内蒙古（蒙）:15
//
//    陕西省（陕）:61
//
//    吉林省（吉）:22
//
//    福建省（闽）:35
//
//    贵州省（贵）:52
//
//    广东省（粤）:44
//
//    青海省（青）:63
//
//    西藏（藏）: 54
//
//    四川省（川）:51
//
//    宁夏回族（宁）:64
//
//    海南省（琼）:46

    private String name;
    private String code;

    ProvinceEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static String getNameByCode(String code) {
        for (ProvinceEnum p : ProvinceEnum.values()) {
            if (p.getCode() == code) {
                return p.name;
            }
        }
        return null;
    }

    public static String getCodeByName(String name) {
        for (ProvinceEnum p : ProvinceEnum.values()) {
            if (p.getName() == name) {
                return p.code;
            }
        }
        return null;
    }
}




