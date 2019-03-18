package cn.globalcash.spring.cloud.client.web.controller;

import cn.globalcash.spring.cloud.client.hystrix.UserRibbonClientHystrixCommand;
import cn.globalcash.spring.cloud.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * @author gh
 * @date 2019/1/29 14:11
 */
@RestController
public class UserRibbonController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${provider.service.name}")
    private String providerServiceName;

    private UserRibbonClientHystrixCommand hystrixCommand;

    @GetMapping("")
    public String index() {
        User user = new User();
        user.setId(1L);
        user.setName("晗");

//        RestTemplate restTemplate = new RestTemplate();
//        String hostName = "localhost";
//        int port = 9090;
//        String url = "http://" + hostName + ":" + port + "/user/save";
//        return restTemplate.postForObject(url,user ,String.class );

        return restTemplate.postForObject("http://" +
                        providerServiceName +
                        "/greeting",
                user, String.class);

    }

    /**
     * 调用 user-service-provider "/user/list" REST 接口，并且直接返回内容
     * 增加 短路功能
     */
    @GetMapping("/user-service-provider/user/list")
    public Collection<User> getUsersList() {
        return  new UserRibbonClientHystrixCommand(providerServiceName, restTemplate).execute();
    }
}
