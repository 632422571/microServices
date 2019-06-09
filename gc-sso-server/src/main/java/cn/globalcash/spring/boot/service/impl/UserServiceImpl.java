package cn.globalcash.spring.boot.service.impl;

import cn.globalcash.spring.boot.core.model.UserInfo;
import cn.globalcash.spring.boot.entity.ReturnT;
import cn.globalcash.spring.boot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: gh
 * @date:2019/6/9
 */
@Service
public class UserServiceImpl implements UserService {
    private static List<UserInfo> mockUserList = new ArrayList<>();
    static {
        for (int i = 0; i <5; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserid(1000+i);
            userInfo.setUsername("user" + (i>0?String.valueOf(i):""));
            userInfo.setPassword("123456");
            mockUserList.add(userInfo);
        }
    }
    @Override
    public ReturnT<UserInfo> findUser(String username, String password) {
        if (username==null || username.trim().length()==0) {
            return new ReturnT<UserInfo>(ReturnT.FAIL_CODE, "Please input username.");
        }
        if (password==null || password.trim().length()==0) {
            return new ReturnT<UserInfo>(ReturnT.FAIL_CODE, "Please input password.");
        }

        // mock user
        for (UserInfo mockUser: mockUserList) {
            if (mockUser.getUsername().equals(username) && mockUser.getPassword().equals(password)) {
                return new ReturnT<UserInfo>(mockUser);
            }
        }

        return new ReturnT<UserInfo>(ReturnT.FAIL_CODE, "username or password is invalid.");
    }
}
