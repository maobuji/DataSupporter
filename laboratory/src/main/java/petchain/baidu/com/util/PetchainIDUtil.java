package petchain.baidu.com.util;

/**
 * Created by Administrator on 2018/3/13.
 */
public class PetchainIDUtil {
    static volatile  long startID=1882330749610185102L;
    public synchronized  static long getID(){
         return startID++;
    }
}
