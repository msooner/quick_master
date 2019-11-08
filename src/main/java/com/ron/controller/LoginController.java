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
     * 用户登录界面
     *
     * @return String
     */
    @RequestMapping("/login")
    public String login() {

        return "page/login";
    }
}
