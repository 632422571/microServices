package cn.globalcash.spring.boot.config;

import cn.globalcash.spring.boot.conf.Conf;
import cn.globalcash.spring.boot.filter.GcSsoWebFilter;
import cn.globalcash.spring.boot.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;

/**
 * @author: gh
 * @date:2019/6/9
 */
@Configuration
public class GcWebSsoConfig implements DisposableBean {
    @Value("${gc.sso.server}")
    private String gcSsoServer;

    @Value("${gc.sso.logout.path}")
    private String gcSsoLogoutPath;

    @Value("${gc-sso.excluded.paths}")
    private String gcSsoExcludedPaths;

    @Value("${gc.sso.redis.address}")
    private String gcSsoRedisAddress;

    @Bean
    public FilterRegistrationBean gcSsoRegistrationBean () {
        // gc-sso, redis init
        JedisUtil.init(gcSsoRedisAddress);
        
        // gc-sso, filter init
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("GcSsoWebFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new GcSsoWebFilter());
        registration.addInitParameter(Conf.SSO_SERVER, gcSsoServer);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, gcSsoLogoutPath);
        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, gcSsoExcludedPaths);

        return registration;
    }

    @Override
    public void destroy() throws Exception {
        // gc-sso, redis close
        JedisUtil.close();
    }
}
