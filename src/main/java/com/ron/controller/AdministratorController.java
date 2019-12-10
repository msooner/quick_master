package com.ron.controller;

import com.ron.common.constants.DigitConstant;
import com.ron.common.constants.StringConsant;
import com.ron.dto.ResponseResult;
import com.ron.entity.SystemUser;
import com.ron.entity.SystemUserDepartment;
import com.ron.entity.SystemUserRole;
import com.ron.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理员管理相关业务逻辑
 *
 * @author Ron
 * @date 2019/12/6
 */
@Controller
@RequestMapping
public class AdministratorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemUserService systemUserService;

    /**
     * 后台首页逻辑
     *
     * @param userCookie 用户cookie
     * @param model 视图model
     * @return
     */
    @RequestMapping("/admin/admin-manager")
    public String adminManager(@CookieValue(value = "user", required = false) String userCookie, Model model) {
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        List<SystemUser> allUsers = systemUserService.getAllUsers();
        model.addAttribute("currentUser", systemUser);
        model.addAttribute("allUsers", allUsers);

        return "page/admin/admin-index";
    }

    /**
     * 添加管理员界面
     *
     * @param userCookie 用户Cookie
     * @param model 页面model
     * @return String
     */
    @RequestMapping("/admin/addManager")
    public String addManager(@CookieValue(value = "user", required = false, defaultValue = "") String userCookie, Model model) {
        //如果用户cookie为空，则跳转至登录页
        if ("".equals(userCookie)) {
            return "redirect:/login";
        }
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //用户所属部门(测试数据)
        List<SystemUserDepartment> departments = new ArrayList();
        departments.add(new SystemUserDepartment(1, "运营部", 0, "admin1"));
        departments.add(new SystemUserDepartment(2, "客服部", 0, "admin2"));
        departments.add(new SystemUserDepartment(3, "技术部", 0, "admin3"));
        departments.add(new SystemUserDepartment(4, "运维部", 0, "admin1"));
        //用户角色
        List<SystemUserRole> systemUserRoles = new ArrayList();
        systemUserRoles.add(new SystemUserRole(1, "超级管理员", 0, "admin1"));
        systemUserRoles.add(new SystemUserRole(2, "系统管理员", 0, "admin1"));
        systemUserRoles.add(new SystemUserRole(3, "普通用户", 0, "admin1"));
        //所有权限列表
        //List<>

        model.addAttribute("userInfo", systemUser);
        model.addAttribute("departments", departments);
        model.addAttribute("systemUserRoles", systemUserRoles);
        model.addAttribute("action", "add");

        return "page/admin/admin-add";
    }

    /**
     * 添加管理员操作
     *
     * @param systemUser 管理员实体
     * @return ResponseResult
     */
    @RequestMapping("/admin/add-manager-info")
    public ResponseResult addManagerInfo(SystemUser systemUser) {
        if (systemUser == null || systemUser.getUsername() == null) {
            return new ResponseResult(DigitConstant.ADD_ADMIN_INFO_ERROR, "", StringConsant.ADD_ADMIN_INFO_ERROR);
        }

        return new ResponseResult(DigitConstant.ADD_ADMIN_SUCCESS, "", StringConsant.ADD_ADMIN_SUCCESS);
    }

    /**
     * 删除管理员
     *
     * @param userId 用户ID
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/delete-admin")
    public ResponseResult deleteManager(@CookieValue(value = "user", required = false) String userCookie, @RequestParam("userId") Integer userId) {
        //检查用户权限
        if (userCookie == null || "".equals(userCookie)) {
            return new ResponseResult(DigitConstant.USER_COOKIE_ERROR, userId, StringConsant.USER_COOKIE_ERROR);
        }
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //用户自己不能删除自己
        if (systemUser.getId() == userId) {
            return new ResponseResult(DigitConstant.DELETE_CURRENT_USER_ERROR, userId, StringConsant.DELETE_CURRENT_USER_ERROR);
        }
        //验证用户是否有删除权限

        //删除失败
        if (systemUserService.deleteUser(userId) <= 0) {
            return new ResponseResult(DigitConstant.DELETE_ADMIN_INFO_FAIL, userId, StringConsant.DELETE_ADMIN_INFO_FAIL);
        }

        return new ResponseResult(DigitConstant.DELETE_ADMIN_INFO_SUCCESS, userId, StringConsant.DELETE_ADMIN_INFO_SUCCESS);
    }
}
