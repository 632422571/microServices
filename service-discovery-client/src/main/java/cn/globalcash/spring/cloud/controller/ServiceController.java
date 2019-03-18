package cn.globalcash.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RefreshScope
@RestController
public class ServiceController {
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 返回所有的服务名称
     *
     * @return
     */
    @GetMapping("/services")
    public List<String> getAllServices() {
        return discoveryClient.getServices();
    }

    @GetMapping("/service/instances/{serviceName}")
    public List<String> getAllServiceInstances(@PathVariable String serviceName) {
        return discoveryClient.getInstances(serviceName)
                .stream()
                .map(s ->
                    s.getServiceId() + "-" + s.getHost() + ":" + s.getPort()
                ).collect(Collectors.toList());
    }

/*如何获取配置文件？
  主要方式有两种
1.通过@value注解的方式
（不能动态更新值，需要重启项目） ​​​​​​​-- 这是别人的说法
 目前我测试，在zookeeper修改配置后，是能获取到新值的
    @Value("${xxxx}")
    public String username ;
2.通过@ConfigurationProperties和@EnableConfigurationProperties方式*/

//根据boostrap.propertes的配置，该注解为
//读取zookeeper的/config/service-discovery-client,dev节点下的key为from的值
    @Value("${from}")
    private String from;

    @RequestMapping("/from")
    //动态修改配置文件的值。
    public String from() {
        return this.from;
    }

    @Value("${com.xxx.userName}")
    private String userName;

    @RequestMapping("/userName")
    public String userName() {
        return this.userName;
    }

}
