package cn.globalcash.spring.cloud.config.controller;

import cn.globalcash.spring.cloud.config.domain.UserConfig;
import cn.globalcash.spring.cloud.config.mapper.UserConfigMapper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConfigController {

    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private UserConfigMapper userConfigMapper;


    @GetMapping("/checkExists")
    public String checkExists (@RequestParam String key) throws Exception {
        //判断是否存在，Stat就是对znode所有属性的一个映射，stat=null表示节点不存在
        Stat stat = curatorFramework.checkExists().forPath("/config/service-discovery-client,dev/" + key);
        return stat.toString();
    }
    @GetMapping("/getAll")
    public List<String> getAll () throws Exception {
        //获取子节点列表
        List<String> list = curatorFramework.getChildren().forPath("/");
        return list;
    }

    @PostMapping("/create/config")
    public boolean create (@RequestBody UserConfig config) throws Exception {
        //增
        curatorFramework.create().creatingParentsIfNeeded()//若创建节点的父节点不存在则先创建父节点再创建子节点
                .forPath("/config/service-discovery-client,dev/" + config.getId(),config.getName().getBytes());
        return true;
    }

    @PostMapping("/load/config")
    public boolean load () throws Exception {
        List<UserConfig> userConfigList = userConfigMapper.getUserConfig();
        for(UserConfig userConfig : userConfigList) {
            curatorFramework.create().creatingParentsIfNeeded()//若创建节点的父节点不存在则先创建父节点再创建子节点
                    .forPath("/config/service-discovery-client,dev/" + userConfig.getId(),userConfig.getName().getBytes());
        }
        return true;
    }
    @PostMapping("/update/config")
    public boolean update (@RequestBody UserConfig config) throws Exception {
        Stat stat = curatorFramework.checkExists().forPath("/config/service-discovery-client,dev/" + config.getId());
        if (null == stat) {
            return false;
        }
        //改
        curatorFramework.setData().forPath("/config/service-discovery-client,dev/" + config.getId(),config.getName().getBytes());
        return true;
    }

    @PostMapping("/update/from")
    public boolean updateFrom (@RequestParam String name) throws Exception {
        Stat stat = curatorFramework.checkExists().forPath("/config/service-discovery-client,dev/from");
        if (null == stat) {
            return false;
        }
        //改
        curatorFramework.setData().forPath("/config/service-discovery-client,dev/from",name.getBytes());
        return true;
    }

    @PostMapping("/get/config")
    public String get (@RequestParam String key) throws Exception {
        //查
            String re = new String(curatorFramework.getData().forPath("/config/service-discovery-client,dev/" + key));//只获取数据内容
//        String re = new String(curatorFramework.getData().storingStatIn(new Stat())//在获取节点内容的同时把状态信息存入Stat对象
//                .forPath("/config/2"));
        return re;
    }

    @PostMapping("/delete/config")
    public boolean delete (@RequestParam String node) throws Exception {
        if (null == node) {
            return false;
        }
        Stat stat = curatorFramework.checkExists().forPath("/" + node);
        if (null == stat) {
            return false;
        }
        //删
        curatorFramework.delete().guaranteed()//保障机制，若未删除成功，只要会话有效会在后台一直尝试删除
                .deletingChildrenIfNeeded()//若当前节点包含子节点
                .withVersion(-1)//指定版本号
                .forPath("/" + node);
        return true;
    }
}
