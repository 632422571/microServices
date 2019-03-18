package cn.globalcash.spring.cloud;

import cn.globalcash.spring.cloud.properties.UserInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 尽可能使用 @EnableDiscoveryClient
//支持多个配置类 {xxx.class,abc.class}
@EnableConfigurationProperties(UserInfo.class)
public class ZkDSClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZkDSClientApplication.class, args);
    }
}

   /* 仪表盘需要zkui参考-- https://github.com/DeemOpen/zkui
   Setup
        1.mvn clean install
        2.Copy the config.cfg to the folder with the jar file. Modify it to point to the zookeeper instance. Multiple zk instances are coma separated. eg: server1:2181,server2:2181. First server should always be the leader.
        3.Run the jar. ( nohup java -jar zkui-2.0-SNAPSHOT-jar-with-dependencies.jar & )
        4.http://localhost:9090
   */