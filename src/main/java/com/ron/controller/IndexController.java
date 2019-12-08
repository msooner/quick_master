package com.ron.controller;

import com.ron.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台首页逻辑
 *
 * @auther Ron
 * @date 2019/10/6
 */
@Controller
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 后台首页逻辑
     *
     * @return
     */
    @RequestMapping("/admin")
    public String index() {

        return "page/index";
    }

    @RequestMapping("/show-error")
    public String showError() {

        return "page/error";
    }
}
