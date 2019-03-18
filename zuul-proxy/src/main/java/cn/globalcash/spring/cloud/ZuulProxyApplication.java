package cn.globalcash.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author gh
 * @date 2019/2/2 11:05
 */

@EnableZuulProxy
@SpringBootApplication
public class ZuulProxyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyApplication.class,args);
    }
}
