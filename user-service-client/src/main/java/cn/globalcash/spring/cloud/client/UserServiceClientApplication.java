package cn.globalcash.spring.cloud.client;

import brave.sampler.Sampler;
import cn.globalcash.spring.cloud.client.stream.UserMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author gh
 * @date 2019/1/29 14:18
 */

// 多个 Ribbon 定义
@RibbonClients({
        @RibbonClient(name = "user-service-provider")
})
//@RibbonClient("user-service-provider") // 指定目标应用名称
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker // 使用服务短路
//@EnableFeignClients(clients = UserService.class) // 声明 UserService 接口作为 Feign Client 调用
//@EnableFeignClients(clients = {UserService.class, TestService.class} )  //定义多个类
@EnableFeignClients(basePackages = "cn.globalcash.spring.cloud.service")  //定义包下的
@EnableBinding(UserMessage.class)
//有时候半天起不来，请检查mq,如：kafka 是否运行正常
public class UserServiceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceClientApplication.class,args);
    }


    //声明 RestTemplate
    @LoadBalanced // RestTemplate 的行为变化
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //添加了Sampler的默认采样bean，不然zipkin无法收集到数据
    @Bean
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }

}
