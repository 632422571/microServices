package cn.globalcash.spring.boot.controller.resolver;

import cn.globalcash.spring.boot.entity.ReturnT;
import cn.globalcash.spring.boot.exception.GcSsoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: gh
 * @date:2019/6/9
 */
public class WebExceptionResolver implements HandlerExceptionResolver {
    private static transient Logger logger = LoggerFactory.getLogger(WebExceptionResolver.class);

    @Nullable
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {
        logger.error("WebExceptionResolver:{}", ex);

        // if json
        boolean isJson = false;
        HandlerMethod method = (HandlerMethod)handler;
        ResponseBody responseBody = method.getMethodAnnotation(ResponseBody.class);
        if (responseBody != null) {
            isJson = true;
        }

        // error result
        ReturnT<String> errorResult = null;
        if (ex instanceof GcSsoException) {
            errorResult = new ReturnT<String>(ReturnT.FAIL_CODE, ex.getMessage());
        } else {
            errorResult = new ReturnT<String>(ReturnT.FAIL_CODE, ex.toString().replaceAll("\n", "<br/>"));
        }

        // response
        ModelAndView mv = new ModelAndView();
        if (isJson) {
            try {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print("{\"code\":"+errorResult.getCode()+", \"msg\":\""+ errorResult.getMsg() +"\"}");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return mv;
        } else {

            mv.addObject("exceptionMsg", errorResult.getMsg());
            mv.setViewName("/common/common.exception");
            return mv;
        }
    }
}
