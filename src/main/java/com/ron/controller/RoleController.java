package com.ron.controller;

import com.ron.common.constants.DigitConstant;
import com.ron.common.constants.StringConsant;
import com.ron.dto.ResponseResult;
import com.ron.entity.SystemModule;
import com.ron.entity.SystemUser;
import com.ron.entity.SystemUserRole;
import com.ron.service.RoleService;
import com.ron.service.SystemModuleService;
import com.ron.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 角色控制类
 *
 * @author Ron 2019/12/13
 */
@Controller
@RequestMapping
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    SystemModuleService systemModuleService;

    /**
     * 角色管理
     *
     * @param model 页面model
     * @return String
     */
    @RequestMapping("/admin/role-manager")
    public String roleManager(Model model) {
        List<SystemUserRole> systemUserRoles = roleService.getAllRoleList();
        model.addAttribute("systemUserRoles", systemUserRoles);

        return "page/role/role-index";
    }

    /**
     * 添加角色页面
     *
     * @param userCookie 用户cookie
     * @param model view model
     * @return String
     */
    @RequestMapping("/admin/add-role")
    public String addRole(@CookieValue(value = "user", required = false) String userCookie, Model model, HttpServletResponse response) throws Exception{
        if (StringUtils.isEmpty(userCookie)) {
            response.sendRedirect("/login");
            return "";
        }
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //获取所有的角色信息
        List<SystemUserRole> roles = roleService.getRoleList();
        //获取所有的模块
        List<SystemModule> modules = systemModuleService.getModuleList();
        //以parentId为key的子模块
        List<SystemModule> allModules = systemModuleService.getAllModuleList();
        Map<Integer, List<SystemModule>> childModuleMap = systemModuleService.getChildModuleMap(allModules);
        model.addAttribute("childModuleMap", childModuleMap);
        model.addAttribute("modules", modules);
        model.addAttribute("systemUser", systemUser);
        model.addAttribute("roles", roles);

        return "page/role/role-add";
    }

    /**
     * 角色授权页面
     *
     * @param userCookie 用户cookie
     * @param model 页面model
     * @return String
     */
    @RequestMapping("/admin/authorization-role")
    public String authorizationRole(@CookieValue(value = "user", required = false) String userCookie, @RequestParam("roleId") Integer roleId,
                                    Model model, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(userCookie) || roleId == null || roleId <= 0) {
            response.sendRedirect("/login");
            return "";
        }
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //获取当前角色列表
        SystemUserRole currentRole = roleService.getRoleInfo(roleId);
        if (currentRole == null) {
            response.sendRedirect("/admin/role-manager");
            return "";
        }
        //判断是否有父级角色信息
        SystemUserRole parentRole = null;
        if (currentRole.getParentId() > 0) {
            parentRole = roleService.getRoleInfo(currentRole.getParentId());
        }
        //获取所有的模块
        List<SystemModule> modules = systemModuleService.getModuleList();
        //以parentId为key的子模块
        List<SystemModule> allModules = systemModuleService.getAllModuleList();
        Map<Integer, List<SystemModule>> childModuleMap = systemModuleService.getChildModuleMap(allModules);
        model.addAttribute("childModuleMap", childModuleMap);
        model.addAttribute("modules", modules);
        model.addAttribute("systemUser", systemUser);
        model.addAttribute("currentRole", currentRole);
        model.addAttribute("parentRole", parentRole);

        return "page/role/role-grant-authorization";
    }

    /**
     * 编辑角色页面
     *
     * @param userCookie 用户cookie
     * @param model view model
     * @param roleId 角色id
     * @return String
     */
    @RequestMapping("/admin/edit-role")
    public String editRole(@CookieValue(value = "user", required = false) String userCookie, @RequestParam("roleId") Integer roleId,
                           Model model, HttpServletResponse response) throws Exception{
        if (userCookie == null) {
            response.sendRedirect("/login");
            return "";
        }
        if (roleId == null || roleId <= 0) {
            response.sendRedirect("/admin/role-manager");
            return "";
        }
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //获取当前角色信息
        SystemUserRole systemUserRole = roleService.getRoleInfo(roleId);
        if (systemUserRole == null) {
            response.sendRedirect("/admin/role-manager");
            return "";
        }
        //获取上级角色信息
        SystemUserRole parentRole = null;
        if (systemUserRole.getParentId() > 0) {
            parentRole = roleService.getRoleInfo(systemUserRole.getParentId());
        }
        //获取一级角色列表
        List<SystemUserRole> roles = roleService.getRoleList();
        model.addAttribute("systemUser", systemUser);
        model.addAttribute("systemUserRole", systemUserRole);
        model.addAttribute("parentRole", parentRole);
        model.addAttribute("roles", roles);

        return "page/role/role-edit";
    }

    /**
     * 添加角色操作
     *
     * @param systemUserRole 角色实体
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/add-role-result")
    public ResponseResult addRoleResult(SystemUserRole systemUserRole) {
        //检测角色信息
        if (systemUserRole.getParentId() == null || systemUserRole.getParentId() < 0 || StringUtils.isEmpty(systemUserRole.getRoleName())) {
            return new ResponseResult(DigitConstant.ADD_ROLE_INFO_ERROR, "", StringConsant.ADD_ROLE_INFO_ERROR);
        }
        //检测角色信息是否存在
        if (roleService.checkRoleIsExists(systemUserRole.getRoleName(), systemUserRole.getParentId())) {
            return new ResponseResult(DigitConstant.ROLE_INFO_IS_EXISTS, "", StringConsant.ROLE_INFO_IS_EXISTS);
        }
        //添加角色信息
        if (! roleService.addRole(systemUserRole)) {
            return new ResponseResult(DigitConstant.ADD_ROLE_FAIL, "", StringConsant.ADD_ROLE_FAIL);
        }

        return new ResponseResult(DigitConstant.ADD_ROLE_SUCCESS, "", StringConsant.ADD_ROLE_SUCCESS);
    }

    /**
     * 编辑角色操作
     *
     * @param systemUserRole 角色实体
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/edit-role-result")
    public ResponseResult editRoleResult(SystemUserRole systemUserRole) {
        //检测角色信息
        if (systemUserRole.getParentId() == null || systemUserRole.getParentId() < 0 || StringUtils.isEmpty(systemUserRole.getRoleName())) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        //检测角色信息是否存在
        SystemUserRole currentRole = roleService.getRoleInfo(systemUserRole.getId());
        if (currentRole == null) {
            return new ResponseResult(DigitConstant.ROLE_INFO_IS_NOT_EXISTS, "", StringConsant.ROLE_INFO_IS_NOT_EXISTS);
        }
        //检查传入参数是否合法
        if (currentRole.getParentId() > 0) {
            SystemUserRole parentRole = roleService.getRoleInfo(currentRole.getParentId());
            if (parentRole != null && parentRole.getRoleName() == systemUserRole.getRoleName()) {
                return new ResponseResult(DigitConstant.EDIT_ROLE_NAME_ERROR, "", StringConsant.EDIT_ROLE_NAME_ERROR);
            }
        }
        //编辑角色信息
        if (! roleService.editRole(systemUserRole)) {
            return new ResponseResult(DigitConstant.EDIT_ROLE_FAIL, "", StringConsant.EDIT_ROLE_FAIL);
        }

        return new ResponseResult(DigitConstant.EDIT_ROLE_SUCCESS, "", StringConsant.EDIT_ROLE_SUCCESS);
    }

    /**
     * 获取一级角色下的子角色列表
     *
     * @param parentId 上级角色
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/get-child-role-list")
    public ResponseResult getChildRoleList(@RequestParam("parentId") Integer parentId) {
        if (parentId == null || parentId < 0) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        List<SystemUserRole> roleList = roleService.getChildRoleList(parentId);

        return new ResponseResult(DigitConstant.GET_ROLE_LIST_SUCCESS, roleList, StringConsant.GET_ROLE_LIST_SUCCESS);
    }

    /**
     * 角色授权
     *
     * @param userCookie 用户cookie
     * @param systemUserRole 用户角色实体
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/authorization-role-result")
    public ResponseResult authorizationRole(@CookieValue(value = "user", required = false) String userCookie, SystemUserRole systemUserRole) {
        if (StringUtils.isEmpty(userCookie)) {
            return new ResponseResult(DigitConstant.USER_COOKIE_ERROR, "", StringConsant.USER_COOKIE_ERROR);
        }
        //检测请求参数
        if (systemUserRole.getId() == null || systemUserRole.getId() < 0 || systemUserRole.getParentId() == null || systemUserRole.getParentId() < 0
                || StringUtils.isEmpty(systemUserRole.getModuleIds())) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        //检测用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        if (systemUser == null || systemUserRole.getCreateBy() == null || ! systemUser.getUsername().equals(systemUserRole.getCreateBy())) {
            return new ResponseResult(DigitConstant.USER_COOKIE_ERROR, "", StringConsant.USER_COOKIE_ERROR);
        }
        //角色权限操作
        if (! roleService.authorizationRole(systemUserRole)) {
            return new ResponseResult(DigitConstant.AUTHORIZATION_FAIL, "", StringConsant.AUTHORIZATION_FAIL);
        }

        return new ResponseResult(DigitConstant.AUTHORIZATION_SUCCESS, "", StringConsant.AUTHORIZATION_SUCCESS);
    }

    /**
     * 删除角色信息
     *
     * @param id 角色ID
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/delete-role")
    public ResponseResult deleteRole(@RequestParam("id") Integer id) {
        //检测传入参数
        if (id == null || id <= 0) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        //删除用户角色
        if (! roleService.deleteRole(id)) {
            return new ResponseResult(DigitConstant.DELETE_ROLE_FAIL, "", StringConsant.DELETE_ROLE_FAIL);
        }
        //如果有子角色，同步删除
        List<SystemUserRole> roleList = roleService.getChildRoleList(id);
        if (roleList.size() > 0) {
            roleService.deleteChildRole(id);
        }

        return new ResponseResult(DigitConstant.DELETE_ROLE_SUCCESS, "", StringConsant.DELETE_ROLE_SUCCESS);
    }
}
