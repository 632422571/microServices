package cn.globalcash.spring.cloud.provider.bus;

import cn.globalcash.spring.cloud.event.UserRemoteApplicationEvent;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

/**
 * 用户服务提供方 Bus 配置
 */
@Configuration
@RemoteApplicationEventScan(basePackageClasses = UserRemoteApplicationEvent.class)
public class UserServiceProviderBusConfiguration {

}
