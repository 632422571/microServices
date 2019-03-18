package cn.globalcash.spring.cloud.fallback;

import cn.globalcash.spring.cloud.service.UserService;
import cn.globalcash.spring.cloud.domain.User;

import java.util.Collections;
import java.util.List;

/**
 * @author gh
 * @date 2019/2/1 15:59
 */
public class UserServiceFallback implements UserService {
    @Override
    public boolean saveUser(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return Collections.emptyList();
    }
}
