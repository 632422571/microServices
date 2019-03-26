package cn.globalcash.spring.cloud.provider.web.controller;

import cn.globalcash.spring.cloud.domain.User;
import cn.globalcash.spring.cloud.provider.mapper.UserMapper;
import cn.globalcash.spring.cloud.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author gh
 * @date 2019/1/29 15:10
 */

@RestController
public class UserServiceProviderController implements UserService {

    @Autowired
    @Qualifier("inMemoryUserService") // 实现 Bean ： InMemoryUserService
    private UserService userService;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private UserMapper userMapper;

    private final static Random random = new Random();

    @GetMapping("/user/data")
    public List<User> getUserData() {
        return  userMapper.getData();
    }


    @GetMapping("/user/poll")
    public Object pollUser() {
        // 获取消息队列中，默认 destination = sf-users-activemq
        return jmsTemplate.receiveAndConvert();
    }

    // 通过方法继承，URL 映射 ："/user/save"
    @Override
    public boolean  saveUser(@RequestBody User user) {
         return userService.saveUser(user);
    }


    @PostMapping("/greeting")
    public String greeting(@RequestBody User user) {
        return "Greeting , " + user + " on port : " + port;
    }

    @HystrixCommand(
            commandProperties = { // Command 配置
                    // 设置操作时间为 100 毫秒
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            },
            fallbackMethod = "fallbackForGetUsers" // 设置 fallback 方法
    )
    // 通过方法继承，URL 映射 ："/user/find/all"
    @Override
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 获取所有用户列表
     *
     * @return
     */
    @HystrixCommand(
            commandProperties = {//Common 配置
                // 设置操作时间为 100 毫秒
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")
            },
            fallbackMethod = "fallbackForGetUsers" // 设置 fallback 方法
    )


    @GetMapping("/user/list")
    public List<User> getUsers() throws InterruptedException {

        long executeTime = random.nextInt(200);

        // 通过休眠来模拟执行时间
        System.out.println("Execute Time : " + executeTime + " ms");

        Thread.sleep(executeTime);

        return userService.findAll();
    }

    /**
     * {@link #getUsers()} 的 fallback 方法
     *
     * @return 空集合
     */
    public List<User> fallbackForGetUsers() {
        return Collections.emptyList();
    }

    @GetMapping("/hello-test")
    public String hello(@RequestParam(value = "id") Long id){
        return "hello feign test" + id;
    }

    @GetMapping("/find/all/user")
    public List<User> findAllUser() {
        System.out.println("findAllUser被调用了");
        return userMapper.getData();
    }
}
