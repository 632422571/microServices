package cn.globalcash.spring.cloud.provider.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author gh
 * @date 2019/2/12 11:10
 */
public interface UserMessage {
    String INPUT = "user-message";

    @Input(INPUT)
    //管道名称
    SubscribableChannel input();

    @Input("activemq-in")
    SubscribableChannel activeMQIn();
}
