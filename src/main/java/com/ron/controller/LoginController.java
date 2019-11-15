package com.ron.controller;

import com.ron.dto.ResponseResult;
import com.ron.entity.SystemUser;
import com.ron.service.SystemUserService;
import com.ron.utils.CookieUtil;
import com.ron.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录逻辑
 *
 * @author Ron
 * @date 2019/11/6
 */
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    SystemUserService systemUserService;

    /**
     * 用户登录界面(默认为登录界面，如果已经登录则跳转至用户中心)
     *
     * @return String
     */
    @RequestMapping({"/", "/login"})
    public String login(HttpServletResponse response, @CookieValue(name = "user", defaultValue = "") String userCookie) {
        //如果用户Cookie不存在，则生成
        if ("".equals(userCookie) || userCookie == null) {
            String randomString = StringUtil.getRandomString(10);
            String userMD5Cookie = StringUtil.getMD5String(randomString, "");
            CookieUtil.setCookie("user", userMD5Cookie);
        } else {
            //如果用户已登录，则跳转至后台首页
            SystemUser systemUser = systemUserService.getUserInfo(userCookie);
            if (systemUser.getId() > 0) {
                return "page/index";
            }
        }

        return "page/login";
    }

    @ResponseBody
    @RequestMapping("/loginResult")
    public ResponseResult<Integer> loginResult() {
        ResponseResult responseResult;

        return new ResponseResult<>(true, 1, 200);
    }
}
