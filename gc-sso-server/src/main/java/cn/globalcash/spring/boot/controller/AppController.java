package cn.globalcash.spring.boot.controller;

import cn.globalcash.spring.boot.core.model.UserInfo;
import cn.globalcash.spring.boot.entity.ReturnT;
import cn.globalcash.spring.boot.login.SsoTokenLoginHelper;
import cn.globalcash.spring.boot.service.UserService;
import cn.globalcash.spring.boot.store.SsoLoginStore;
import cn.globalcash.spring.boot.store.SsoSessionIdHelper;
import cn.globalcash.spring.boot.user.GcSsoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author: gh
 * @date:2019/6/9
 */
@RestController
public class AppController {
    @Autowired
    private UserService userService;

    /**
     * Login
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/appLogin")
    public ReturnT<String> appLogin(String username, String password) {
        // valid login
        ReturnT<UserInfo> result = userService.findUser(username, password);
        if (result.getCode() != ReturnT.SUCCESS_CODE) {
            return new ReturnT<String>(result.getCode(), result.getMsg());
        }

        // 1、make gc-sso user
        GcSsoUser gcUser = new GcSsoUser();
        gcUser.setUserid(String.valueOf(result.getData().getUserid()));
        gcUser.setUsername(result.getData().getUsername());
        gcUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        gcUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
        gcUser.setExpireFreshTime(System.currentTimeMillis());


        // 2、generate sessionId + storeKey
        String sessionId = SsoSessionIdHelper.makeSessionId(gcUser);

        // 3、login, store storeKey
        SsoTokenLoginHelper.login(sessionId, gcUser);

        // 4、return sessionId
        return new ReturnT<String>(sessionId);
    }

    /**
     * logout
     * @param sessionId
     * @return
     */
    @PostMapping("/appLogout")
    public ReturnT<String> appLogout(String sessionId) {
        // logout, remove storeKey
        SsoTokenLoginHelper.logout(sessionId);
        return ReturnT.SUCCESS;
    }

    /**
     * logincheck
     * @param sessionId
     * @return
     */
    @GetMapping("/logincheck")
    public ReturnT<GcSsoUser> logincheck(String sessionId) {

        // logout
        GcSsoUser gcUser = SsoTokenLoginHelper.loginCheck(sessionId);
        if (gcUser == null) {
            return new ReturnT<GcSsoUser>(ReturnT.FAIL_CODE, "sso not login.");
        }
        return new ReturnT<GcSsoUser>(gcUser);
    }
}
