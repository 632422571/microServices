package cn.globalcash.spring.boot.config;

import cn.globalcash.spring.boot.store.SsoLoginStore;
import cn.globalcash.spring.boot.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: gh
 * @date:2019/6/9
 */
@Configuration
public class GcSsoConfig implements InitializingBean,DisposableBean {
    @Value("${gc.sso.redis.address}")
    private String redisAddress;

    @Value("${gc.sso.redis.expire.minite}")
    private int redisExpireMinite;
    @Override
    public void destroy() throws Exception {
        JedisUtil.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SsoLoginStore.setRedisExpireMinite(redisExpireMinite);
        JedisUtil.init(redisAddress);
    }
}
