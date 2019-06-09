package cn.globalcash.spring.boot.service;

import cn.globalcash.spring.boot.core.model.UserInfo;
import cn.globalcash.spring.boot.entity.ReturnT;

/**
 * @author: gh
 * @date:2019/6/9
 */
public interface UserService {
    public ReturnT<UserInfo> findUser(String username, String password);
}
