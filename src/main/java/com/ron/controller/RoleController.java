package com.ron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 角色控制类
 *
 * @author Ron 2019/12/13
 */
@Controller
@RequestMapping
public class RoleController {

    /**
     * 角色管理
     *
     * @return String
     */
    @RequestMapping("/admin/role-manager")
    public String roleManager() {

        return "page/role/role-index";
    }
}
