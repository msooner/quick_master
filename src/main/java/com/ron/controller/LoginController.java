package com.ron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录逻辑
 *
 * @author Ron
 * @date 2019/11/6
 */
@Controller
@RequestMapping
public class LoginController {

    /**
     * 用户登录界面(默认为登录界面，如果已经登录则跳转至用户中心)
     *
     * @return String
     */
    @RequestMapping({"/", "/login"})
    public String login() {
        //从redis中获取用户Id，如果为空，则展示登录，否则展示后台首页


        return "page/login";
    }
}
