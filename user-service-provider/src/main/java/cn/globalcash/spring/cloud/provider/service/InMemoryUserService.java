package cn.globalcash.spring.cloud.provider.service;

import cn.globalcash.spring.cloud.domain.User;
import cn.globalcash.spring.cloud.service.UserService;
import cn.globalcash.spring.cloud.provider.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gh
 * @date 2019/2/1 14:23
 */
@Service("inMemoryUserService") // Bean 名称
public class InMemoryUserService implements UserService {

    @Autowired
    private UserMapper userMapper;

    private Map<Long, User> repository = new ConcurrentHashMap<>();

    @Override
    public boolean saveUser(@RequestBody User user) {
        return repository.put(user.getId(), user) == null;
    }

    @Override
    public List<User> findAll() {
        return  userMapper.getData();
//        return new ArrayList(repository.values());
    }
}
