package petchain.baidu.com.util;

/**
 * Created by Administrator on 2018/3/13.
 */
public class PetchainIDUtil {
//     private static volatile  long startID=1882330749610185102L;
    private static volatile  long startID=1997628425121158679L;
    public synchronized  static long getID(){
         return startID++;
    }
}
