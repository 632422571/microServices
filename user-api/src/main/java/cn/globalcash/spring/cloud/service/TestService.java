package cn.globalcash.spring.cloud.service;

import cn.globalcash.spring.cloud.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author gh
 * @date 2019/2/2 9:42
 */

@FeignClient(name = "${user.service.name}") // 利用占位符避免未来整合硬编码
public interface TestService {

    @GetMapping("/hello-test")
    public String hello(@RequestParam(value = "id") Long id);

    @GetMapping("/find/all/user")
    public List<User> findAllUser();

}
