package com.ron.controller;

import com.ron.common.constants.DigitConstant;
import com.ron.common.constants.StringConsant;
import com.ron.dto.ResponseResult;
import com.ron.entity.SystemUser;
import com.ron.entity.SystemUserDepartment;
import com.ron.entity.SystemUserRole;
import com.ron.service.DepartmentService;
import com.ron.service.RoleService;
import com.ron.service.SystemUserService;
import com.ron.utils.StringUtil;
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

import javax.management.relation.Role;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleService roleService;

    /**
     * 后台首页逻辑
     *
     * @param userCookie 用户cookie
     * @param model 视图model
     * @return
     */
    @RequestMapping("/admin/admin-manager")
    public String adminManager(@CookieValue(value = "user", required = false) String userCookie, HttpServletResponse response,Model model) throws Exception {
        //检测用户信息，不合法将跳转
        systemUserService.redirectSystemUser(response, "/admin", userCookie);
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        System.out.println(systemUser);
        List<SystemUser> allUsers = systemUserService.getSystemUserList();
        System.out.println(allUsers);
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
    public String addManager(@CookieValue(value = "user", required = false, defaultValue = "") String userCookie, HttpServletResponse response, Model model) throws Exception {
        //检测用户信息，不合法将跳转
        systemUserService.redirectSystemUser(response, "/admin", userCookie);
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //用户所属部门(测试数据)
        List<SystemUserDepartment> systemUserDepartments = departmentService.getDepartmentList();

        //用户角色
        List<SystemUserRole> systemUserRoles = roleService.getAllChildRoleList();

        model.addAttribute("userInfo", systemUser);
        model.addAttribute("departments", systemUserDepartments);
        model.addAttribute("systemUserRoles", systemUserRoles);

        return "page/admin/admin-add";
    }

    /**
     * 编辑管理员界面
     *
     * @param userCookie 用户Cookie
     * @param model 用户model
     * @param response response
     * @return String
     */
    @RequestMapping("/admin/admin-edit")
    public String editManager(@CookieValue("user") String userCookie, @RequestParam(value = "userId", required = true) Integer userId,
                              Model model, HttpServletResponse response) throws Exception {
        //检测用户信息，不合法将跳转
        systemUserService.redirectSystemUser(response, "/admin", userCookie);
        //检测传入参数
        if (userId == null || userId <= 0) {
            response.sendRedirect("/admin/admin-manager");
            return "";
        }
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //根据用户id获取用户信息，只有超级系统管理员才能修改管理员信息
        SystemUser currentUser = systemUserService.getUserById(userId);
        if (currentUser == null || systemUser == null || systemUser.getRoleId() != DigitConstant.SYSTEM_ROLE_ID) {
            response.sendRedirect("/admin/admin-manager");
            return "";
        }
        Boolean canChangeRole =  true;
        //判断是否能改变角色：系统管理员不能修改角色
        if (systemUser.getRoleId() == DigitConstant.SYSTEM_ROLE_ID && systemUser.getId() == currentUser.getId()) {
            canChangeRole = false;
        }

        //用户所属部门(测试数据)
        List<SystemUserDepartment> systemUserDepartments = departmentService.getDepartmentList();

        //用户角色
        List<SystemUserRole> systemUserRoles = roleService.getAllChildRoleList();
        //追加系统管理员到角色列表
        SystemUserRole role = new SystemUserRole(1, "系统管理员",0, null);
        systemUserRoles.add(role);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("canChangeRole", canChangeRole);
        model.addAttribute("systemUser", systemUser);
        model.addAttribute("departments", systemUserDepartments);
        model.addAttribute("systemUserRoles", systemUserRoles);

        return "page/admin/admin-edit";
    }

    /**
     * 添加管理员操作
     *
     * @param systemUser 管理员实体
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/add-manager-result")
    public ResponseResult addManagerInfo(@CookieValue(value = "user", required = false) String userCookie, SystemUser systemUser) {
        //检测传入参数
        if (systemUser == null || StringUtils.isEmpty(systemUser.getUsername()) || StringUtils.isEmpty(systemUser.getPassword()) || StringUtils.isEmpty(systemUser.getEmail())) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        if (systemUser.getDeptId() == null || systemUser.getDeptId() <= 0 || systemUser.getRoleId() == null || systemUser.getRoleId() <= 0) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        //检测操作用户
        SystemUser currentUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //检测添加的用户名是否存在
        if (systemUserService.getUserByName(systemUser.getUsername()) == null) {
            return new ResponseResult(DigitConstant.MANAGER_USERNAME_ERROR, "", StringConsant.MANAGER_USERNAME_ERROR);
        }
        if (currentUser == null || StringUtils.isEmpty(systemUser.getCreatedBy()) || !currentUser.getUsername().equals(systemUser.getCreatedBy())) {
            return new ResponseResult(DigitConstant.ILLEGAL_OPERATION, "", StringConsant.ILLEGAL_OPERATION);
        }
        //添加管理员操作
        if (! systemUserService.addUser(systemUser)) {
            return new ResponseResult(DigitConstant.ADD_ADMIN_INFO_ERROR, "", StringConsant.ADD_ADMIN_INFO_ERROR);
        }

        return new ResponseResult(DigitConstant.ADD_ADMIN_SUCCESS, "", StringConsant.ADD_ADMIN_SUCCESS);
    }

    /**
     * 编辑管理员信息操作
     *
     * @param userCookie 用户cookie
     * @param systemUser 用户实体
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/edit-manager-result")
    public ResponseResult editManagerResult(@CookieValue("user") String userCookie, SystemUser systemUser) {
        //检测传入参数
        if (systemUser == null || StringUtils.isEmpty(systemUser.getUsername()) || StringUtils.isEmpty(systemUser.getPassword()) || StringUtils.isEmpty(systemUser.getEmail())) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        if (systemUser.getId() == null || systemUser.getId() <= 0 || systemUser.getDeptId() == null || systemUser.getDeptId() <= 0 || systemUser.getRoleId() == null || systemUser.getRoleId() <= 0) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        //检测操作用户
        SystemUser currentUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        if (currentUser == null || StringUtils.isEmpty(systemUser.getCreatedBy()) || currentUser.getRoleId() != DigitConstant.SYSTEM_ROLE_ID) {
            return new ResponseResult(DigitConstant.ILLEGAL_OPERATION, "", StringConsant.ILLEGAL_OPERATION);
        }
        //检测添加的用户是否与当前用户相同
        if (currentUser.getUsername() == systemUser.getUsername()) {
            return new ResponseResult(DigitConstant.MANAGER_USERNAME_ERROR, "", StringConsant.MANAGER_USERNAME_ERROR);
        }

        //检测用户密码是否修改
        if (! "".equals(systemUser.getPassword()) &&  systemUser.getPassword() != null) {
            String md5Password = StringUtil.getMD5String(systemUser.getPassword(), StringConsant.PASSWORD_SALT);
            if (md5Password == currentUser.getPassword()) {
                systemUser.setPassword(null);
            } else {
                systemUser.setPassword(md5Password);
            }
        }
        //编辑管理员信息
        if (! systemUserService.editUser(systemUser)) {
            return new ResponseResult(DigitConstant.EDIT_ADMIN_INFO_ERROR, "", StringConsant.EDIT_ADMIN_INFO_ERROR);
        }

        return new ResponseResult(DigitConstant.EDIT_ADMIN_SUCCESS, "", StringConsant.EDIT_ADMIN_SUCCESS);
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
        //删除失败
        if (systemUserService.deleteUser(userId) <= 0) {
            return new ResponseResult(DigitConstant.DELETE_ADMIN_INFO_FAIL, userId, StringConsant.DELETE_ADMIN_INFO_FAIL);
        }

        return new ResponseResult(DigitConstant.DELETE_ADMIN_INFO_SUCCESS, userId, StringConsant.DELETE_ADMIN_INFO_SUCCESS);
    }
}
