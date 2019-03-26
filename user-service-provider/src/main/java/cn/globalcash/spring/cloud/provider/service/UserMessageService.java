package cn.globalcash.spring.cloud.provider.service;

import cn.globalcash.spring.cloud.domain.User;
import cn.globalcash.spring.cloud.provider.stream.UserMessage;
import cn.globalcash.spring.cloud.service.UserService;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.util.ByteSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author gh
 * @date 2019/2/12 11:16
 */
//别忘了加注解
@Service
public class UserMessageService {
    @Autowired
    private UserMessage userMessage;

    @Autowired
    @Qualifier("inMemoryUserService")
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @ServiceActivator(inputChannel = UserMessage.INPUT)
    public void listen(byte[] data) throws IOException {
        System.out.println("Subscribe by @ServiceActivator");
        saveUser(data);
    }

//    @StreamListener(INPUT)
//    public void onMessage(byte[] data) throws IOException {
//        System.out.println("Subscribe by @StreamListener");
//        saveUser(data);
//    }
    //用byte[] 处理传输的数据还有些问题，故用下边这种存储
    @StreamListener(UserMessage.INPUT)
    public void onMessage(String data) throws IOException {
        System.out.println("Subscribe by @StreamListener");
        User user = objectMapper.readValue(data, User.class);
        userService.saveUser(user);
    }

    private void saveUser(String data) throws IOException {
        User user = objectMapper.readValue(data, User.class);
        userService.saveUser(user);
    }

    private void saveUser(byte[] data) {
        // message body 是字节流 byte[]
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            User user = (User) objectInputStream.readObject(); // 反序列化成 User 对象
            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {

        SubscribableChannel subscribableChannel = userMessage.input();
        subscribableChannel.subscribe(message -> {
            System.out.println("Subscribe by SubscribableChannel");
            String contentType = message.getHeaders().get("contentType", String.class);
            if ("text/plain".equals(contentType)) {
                try {
                    saveUser((String) message.getPayload());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // message body 是字节流 byte[]
                byte[] body = (byte[]) message.getPayload();
                saveUser(body);
            }
        });

        // 监听 ActiveMQ Stream
        userMessage.activeMQIn().subscribe(message -> {

            if (message instanceof GenericMessage) {
                GenericMessage genericMessage = (GenericMessage) message;
                User user = (User)this.byteToObject(((ByteSequence) genericMessage.getPayload()).getData(),User.class);
//                User user = (User) genericMessage.getPayload();
                userService.saveUser(user);
            }
        });
    }

    @StreamListener("activemq-in")
    public void onUserMessage(User user) throws IOException {
        System.out.println("Subscribe by @StreamListener");
        userService.saveUser(user);
    }

    /**
     * 该方法传ByteArrayInputStream.getByte
     * byte转对象
     * @param bytes
     * @return
     */
    /*private Object byteToObject(byte[] bytes) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }*/

    private Object byteToObject(byte[] bytes,Class objClass) {
        return JSON.parseObject(new String(bytes),objClass);
    }

    public static void main(String[] args) {
        User user=new User();
        user.setId(1l);
        user.setName("Hank");
        byte[] ub=JSON.toJSONString(user).getBytes();
        System.out.println(JSON.toJSONString(user));
        System.out.println(JSON.parseObject(new String(ub),User.class).getName());
    }

    /*@EventListener
    public void onRefreshRemoteApplicationEvent(RefreshRemoteApplicationEvent event) {
        User user = (User) event.getSource();
        userService.saveUser(user);
    }*/
}
