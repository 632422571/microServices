package cn.globalcash.spring.boot.controller;

import cn.globalcash.spring.boot.conf.Conf;
import cn.globalcash.spring.boot.entity.ReturnT;
import cn.globalcash.spring.boot.user.GcSsoUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: gh
 * @date:2019/6/9
 */
@RestController
public class IndexController {
    @RequestMapping("/")
    public ReturnT<GcSsoUser> index(HttpServletRequest request) {
        GcSsoUser gcUser = (GcSsoUser) request.getAttribute(Conf.SSO_USER);
        return new ReturnT<GcSsoUser>(gcUser);
    }
}
