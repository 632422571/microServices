package cn.globalcash.spring.cloud.client.hystrix;

import cn.globalcash.spring.cloud.domain.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 增加编程方式的短路实现，此方法可动态配置短路时间
 * @author gh
 * @date 2019/2/1 15:06
 */
public class UserRibbonClientHystrixCommand extends HystrixCommand<Collection> {

    private final String providerServiceName;

    private final RestTemplate restTemplate;


    public UserRibbonClientHystrixCommand(String providerServiceName,RestTemplate restTemplate){
        super(HystrixCommandGroupKey.Factory.asKey(
                "User-Service-Client"),
                100);
        this.providerServiceName = providerServiceName;
        this.restTemplate = restTemplate;
    }

    /**
     * 主逻辑实现
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Collection run() throws Exception {
        return restTemplate.getForObject("http://" + providerServiceName + "/user/list",Collection.class);
    }

    /**
     * Fallback 实现
     *
     * @return 空集合
     */
    protected List<User> getFallback() {
        return Collections.emptyList();
    }
}
