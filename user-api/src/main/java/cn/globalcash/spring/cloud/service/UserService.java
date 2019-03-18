package cn.globalcash.spring.cloud.service;

import cn.globalcash.spring.cloud.fallback.UserServiceFallback;
import cn.globalcash.spring.cloud.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 *  用户服务
 * @author gh
 * @date 2019/2/1 14:20
 */
@FeignClient(name = "${user.service.name}",fallback = UserServiceFallback.class) // 利用占位符避免未来整合硬编码
public interface UserService  {

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("/user/save")
    public boolean saveUser(@RequestBody User user) ;

    /**
     * 查询所有的用户列表
     * @return
     */
    @GetMapping("/user/find/all")
    public List<User> findAll();

}
