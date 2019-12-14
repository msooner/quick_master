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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    SystemUserService systemUserService;


    /**
     * 后台首页逻辑
     *
     * @param model 视图model
     * @return
     */
    @RequestMapping("/admin/department-manager")
    public String departmentManager(Model model) {

        List<SystemUserDepartment> departments = departmentService.getDepartmentList();
        model.addAttribute("departments", departments);

        return "page/department/department-index";
    }

    /**
     * 添加部门信息页面
     *
     * @param userCookie 用户cookie
     * @param model viewModel
     * @return String
     */
    @RequestMapping("/admin/add-department")
    public String addDepartment(@CookieValue(value = "user", required = false) String userCookie, Model model) {
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //所有部门信息
        List<SystemUserDepartment> departments = departmentService.getDepartmentList();
        model.addAttribute("systemUser", systemUser);
        model.addAttribute("departments", departments);

        return "page/department/department-add";
    }

    /**
     * 编辑部门信息页面
     *
     * @param userCookie 用户cookie
     * @param model viewModel
     * @return String
     */
    @RequestMapping("/admin/edit-department")
    public String editDepartment(@CookieValue(value = "user", required = false) String userCookie,
                                 @RequestParam("departmentId") Integer departmentId, Model model) {
        //检测部门id
        if (departmentId == null || departmentId <= 0) {
            return "redirect:/admin/department-manager";
        }
        //检测部门信息
        SystemUserDepartment systemUserDepartment = departmentService.getDepartmentInfo(departmentId);
        if (systemUserDepartment == null) {
            return "redirect:/admin/department-manager";
        }
        model.addAttribute("systemUserDepartment", systemUserDepartment);
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //所有部门信息
        List<SystemUserDepartment> departments = departmentService.getDepartmentList();
        model.addAttribute("systemUser", systemUser);
        model.addAttribute("departments", departments);

        return "page/department/department-edit";
    }

    /**
     * 添加部门信息
     *
     * @param systemUserDepartment 部门实体
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/add-department-result")
    public ResponseResult addDepartmentResult(SystemUserDepartment systemUserDepartment) {
        System.out.println(systemUserDepartment);
        //检测需要添加的部门信息
        if (StringUtils.isEmpty(systemUserDepartment.getParentId()) || StringUtils.isEmpty(systemUserDepartment.getDepartmentName())
                || StringUtils.isEmpty(systemUserDepartment.getCreateBy())) {
            return new ResponseResult(DigitConstant.ADD_DEPARTMENT_INFO_ERROR, "", StringConsant.ADD_DEPARTMENT_INFO_ERROR);
        }
        //检查部门信息是否存在
        if (departmentService.checkDepartmentIsExists(systemUserDepartment.getDepartmentName(), systemUserDepartment.getParentId())) {
            return new ResponseResult(DigitConstant.DEPARTMENT_INFO_ISEXISTS, "", StringConsant.DEPARTMENT_INFO_ISEXISTS);
        }
        //添加部门信息
        if (! departmentService.addDepartment(systemUserDepartment)) {
            return new ResponseResult(DigitConstant.ADD_DEPARTMENT_FAIL, "", StringConsant.ADD_DEPARTMENT_FAIL);
        }
        return new ResponseResult(DigitConstant.ADD_DEPARTMENT_SUCCESS, "", StringConsant.ADD_DEPARTMENT_SUCCESS);
    }

    /**
     * 编辑部门信息
     *
     * @param systemUserDepartment 部门实体
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/edit-department-result")
    public ResponseResult editDepartmentResult(SystemUserDepartment systemUserDepartment) {
        //检测需要编辑的部门信息
        if (systemUserDepartment.getParentId() == null || systemUserDepartment.getParentId() < 0
                || StringUtils.isEmpty(systemUserDepartment.getDepartmentName()) || StringUtils.isEmpty(systemUserDepartment.getCreateBy())) {
            return new ResponseResult(DigitConstant.EDIT_DEPARTMENT_INFO_ERROR, "", StringConsant.EDIT_DEPARTMENT_INFO_ERROR);
        }
        //检测要编辑的部门信息是否存在
        if (departmentService.checkDepartmentIsExists(systemUserDepartment.getDepartmentName(), systemUserDepartment.getParentId())) {
            return new ResponseResult(DigitConstant.DEPARTMENT_INFO_ISEXISTS, "", StringConsant.DEPARTMENT_INFO_ISEXISTS);
        }
        //检测部门是否正在
        if (departmentService.getDepartmentInfo(systemUserDepartment.getId()) == null) {
            return new ResponseResult(DigitConstant.DEPARTMENT_INFO_NOT_EXISTS, "", StringConsant.DEPARTMENT_INFO_NOT_EXISTS);
        }
        //编辑部门信息
        if (! departmentService.editDepartment(systemUserDepartment)) {
            return new ResponseResult(DigitConstant.EDIT_DEPARTMENT_FAIL, "", StringConsant.EDIT_DEPARTMENT_FAIL);
        }

        return new ResponseResult(DigitConstant.EDIT_DEPARTMENT_SUCCESS, "", StringConsant.EDIT_DEPARTMENT_SUCCESS);
    }

    /**
     * 级联删除部门信息
     *
     * @param departmentId 部门ID
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/delete-department")
    public ResponseResult deleteDepartment(@RequestParam("departmentId") Integer departmentId) {
        //验证参数
        if (departmentId == null || departmentId <= 0) {
            return new ResponseResult(DigitConstant.ADD_DEPARTMENT_INFO_ERROR, "", StringConsant.ADD_DEPARTMENT_INFO_ERROR);
        }
        //检测部门是否正在
        SystemUserDepartment systemUserDepartment = departmentService.getDepartmentInfo(departmentId);
        if (systemUserDepartment == null) {
            return new ResponseResult(DigitConstant.DEPARTMENT_INFO_NOT_EXISTS, "", StringConsant.DEPARTMENT_INFO_NOT_EXISTS);
        }
        //删除部门信息(如果当前部门下面有下级部门，则同时删除，目前只支持二级部门)
        if (! departmentService.deleteDepartment(departmentId)) {
            return new ResponseResult(DigitConstant.DELETE_DEPARTMENT_FAIL, "", StringConsant.DELETE_DEPARTMENT_FAIL);
        }
        if (systemUserDepartment.getParentId() == 0) {
            departmentService.deleteChildDepartment(systemUserDepartment.getId());
        }

        return new ResponseResult(DigitConstant.DELETE_DEPARTMENT_SUCCESS, departmentId, StringConsant.DELETE_DEPARTMENT_SUCCESS);
    }

}
