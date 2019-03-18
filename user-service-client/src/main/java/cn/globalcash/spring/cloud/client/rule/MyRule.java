package cn.globalcash.spring.cloud.client.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class MyRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {

        ILoadBalancer loadBalancer = getLoadBalancer();

        //获取所有可达服务器列表
//        List<Server> servers = loadBalancer.getReachableSer vers();
        //暂时忽略健康的做法
        List<Server> servers = loadBalancer.getAllServers();
        if (servers.isEmpty()) {
            return null;
        }

        // 永远选择最后一台可达服务器
        Server targetServer = servers.get(servers.size() - 1);
        return targetServer;
    }

}