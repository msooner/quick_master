package com.ron.common.interceptors;

import com.ron.service.SystemUserService;
import com.ron.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录拦截器，主要判断用户身份
 *
 * @author Ron 2019/11.29
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private SystemUserService systemUserService;

    /**
     * 在业务处理器处理请求之前被调用 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),
     * 再退出拦截器链, 如果返回true 执行下一个拦截器,
     * 直到所有的拦截器都执行完毕 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     *
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userCookie = CookieUtil.getCookieValue("user");
        //如果用户未登录则跳转至登录页面
        if (StringUtils.isEmpty(userCookie) || ! systemUserService.checkUserIsLogged(userCookie)) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     *
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器
     * @param modelAndView modelView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        //log.info("TestInterceptor postHandle....");
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * 当有拦截器抛出异常时,
     * 会从当前拦截器往回执行所有的拦截器的afterCompletion()
     *
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器
     * @param ex 异常
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        //log.info("TestInterceptor afterCompletion....");
    }

}
