package cn.globalcash.spring.cloud.stream.binder.activemq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.Binder;

/**
 * ActiveMQ Stream Binder 自动装配
 */
@Configuration
@ConditionalOnMissingBean(Binder.class)
public class ActiveMQStreamBinderAutoConfiguration {
    @Bean
    public ActiveMQMessageChannelBinder activeMQMessageChannelBinder() {
        return new ActiveMQMessageChannelBinder();
    }
}
