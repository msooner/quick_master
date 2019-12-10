package com.ron.controller;

import com.ron.common.constants.DigitConstant;
import com.ron.common.constants.StringConsant;
import com.ron.dto.ResponseResult;
import com.ron.entity.SystemUser;
import com.ron.entity.SystemUserDepartment;
import com.ron.entity.SystemUserRole;
import com.ron.service.DepartmentService;
import com.ron.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门管理相关业务逻辑
 *
 * @author Ron
 * @date 2019/12/6
 */
@Controller
@RequestMapping
public class DepartmentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DepartmentService departmentService;

    /**
     * 后台首页逻辑
     *
     * @param model 视图model
     * @return
     */
    @RequestMapping("/admin/department-manager")
    public String adminManager(Model model) {

        List<SystemUserDepartment> departments = departmentService.getDepartmentList();
        model.addAttribute("departments", departments);

        return "page/department/department-index";
    }


}
