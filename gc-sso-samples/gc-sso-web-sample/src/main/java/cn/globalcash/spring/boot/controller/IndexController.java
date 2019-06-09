package cn.globalcash.spring.boot.controller;

import cn.globalcash.spring.boot.conf.Conf;
import cn.globalcash.spring.boot.entity.ReturnT;
import cn.globalcash.spring.boot.user.GcSsoUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: gh
 * @date:2019/6/9
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {

        GcSsoUser gcUser = (GcSsoUser) request.getAttribute(Conf.SSO_USER);
        model.addAttribute("gcUser", gcUser);
        return "index";
    }

    @GetMapping("/json")
    @ResponseBody
    public ReturnT<GcSsoUser> json(Model model, HttpServletRequest request) {
        GcSsoUser gcUser = (GcSsoUser) request.getAttribute(Conf.SSO_USER);
        return new ReturnT(gcUser);
    }
}
