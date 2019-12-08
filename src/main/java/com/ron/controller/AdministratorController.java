package com.ron.controller;

import com.ron.common.constants.DigitConstant;
import com.ron.common.constants.StringConsant;
import com.ron.dto.ResponseResult;
import com.ron.entity.SystemUser;
import com.ron.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @param model 视图model
     * @return
     */
    @RequestMapping("/admin/admin-manager")
    public String adminManager(Model model) {

        List<SystemUser> allUsers = systemUserService.getAllUsers();
        model.addAttribute("allUsers", allUsers);

        return "page/admin/admin-index";
    }

    /**
     * 添加管理员
     *
     * @param systemUser 管理员实体
     * @return ResponseResult
     */
    public ResponseResult addManager(SystemUser systemUser) {
        if (systemUser == null || systemUser.getUsername() == null) {
            return new ResponseResult(DigitConstant.ADD_ADMIN_INFO_ERROR, "", StringConsant.ADD_ADMIN_INFO_ERROR);
        }

        return new ResponseResult(DigitConstant.ADD_ADMIN_SUCCESS, "", StringConsant.ADD_ADMIN_SUCCESS);
    }
}
