package com.ron.controller;

import com.ron.entity.SystemUser;
import com.ron.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * 后台首页逻辑
 *
 * @auther Ron
 * @date 2019/10/6
 */
@Controller
public class IndexController {

//    @Autowired
//    private SystemUserService systemUserService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/index")
    public String index() {

        return "page/index";
    }
}
