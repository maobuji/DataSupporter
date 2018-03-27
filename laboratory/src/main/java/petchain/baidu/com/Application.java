package petchain.baidu.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Administrator on 2018/3/27.
 */
@SpringBootApplication

public class Application {
    public static void main(String[] args) {
        /*Spring-boot已经集成了tomcat，main函数被执行时，SpringApplication引导应用启动spring
        进而启动tomcat启动应用*/
        SpringApplication.run(Application.class, args);
    }
}
