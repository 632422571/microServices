package cn.globalcash.spring.cloud.client.web.controller;

import cn.globalcash.spring.cloud.client.stream.UserMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.globalcash.spring.cloud.domain.User;
import cn.globalcash.spring.cloud.service.TestService;
import cn.globalcash.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 注意：官方建议 客户端和服务端不要同时实现 Feign 接口
 * 这里的代码只是一个说明，实际情况最好使用组合的方式，而不是继承
 * @author gh
 * @date 2019/2/1 16:11
 */
@RestController
public class UserServiceClientController implements UserService,TestService {

    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public UserServiceClientController(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Autowired
    private UserMessage userMessage;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JmsTemplate jmsTemplate;


    @PostMapping("/user/save/message")
    public boolean saveUserByMessage(@RequestBody User user) {
        ListenableFuture<SendResult<String,Object>> future = kafkaTemplate.send("gc-users", user);
        return future.isDone();
    }

    @PostMapping("/user/save/message/rabbit")
    public boolean saveUserByRabbitMessage(@RequestBody User user) throws JsonProcessingException {
        MessageChannel messageChannel = userMessage.output();
        // User 序列化成 JSON
        String payload = objectMapper.writeValueAsString(user);
        GenericMessage<String> message = new GenericMessage<String>(payload);
        // 发送消息
        return messageChannel.send(message);
    }

    @PostMapping("/user/save/message/activemq")
    public boolean saveUserByActiveMQMessage(@RequestBody User user) throws Exception {
        jmsTemplate.convertAndSend(user);
        return true;
    }

    @PostMapping("/user/save/message/activemq/stream")
    public boolean saveUserByActiveMQStream(@RequestBody User user) throws Exception {
        MessageChannel messageChannel = userMessage.activeMQOut();
        GenericMessage message = new GenericMessage(user);
        //字节输出流
//        ByteArrayOutputStream bos=new ByteArrayOutputStream(JSON.)。。。
        return messageChannel.send(message);
    }

    @Override
    public boolean saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @Override
    public List<User> findAll() {
        return userService.findAll();
    }

    @Override  //若通过继承接口的方式，可继承接口的映射；也可以自己定义一个新的映射，如下
    public String hello(@RequestParam(value = "id") Long id) {
        return testService.hello(id);
    }

    @GetMapping("/hello-test2")
    public String hello2(@RequestParam(value = "id") Long id) {
        return testService.hello(id);
    }

    @GetMapping("/find/all/user")
    public List<User> findAllUser() {
        return testService.findAllUser();
    }

}
