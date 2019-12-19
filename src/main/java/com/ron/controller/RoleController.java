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
    public String addRole(@CookieValue(value = "user", required = false) String userCookie, Model model) {
        if (userCookie == null) {
            return "redirect:/login";
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
        System.out.println(childModuleMap);
        model.addAttribute("childModuleMap", childModuleMap);
        model.addAttribute("modules", modules);
        model.addAttribute("systemUser", systemUser);
        model.addAttribute("roles", roles);

        return "page/role/role-add";
    }

    /**
     * 编辑角色页面
     *
     * @param userCookie 用户cookie
     * @param model view model
     * @return String
     */
    public String editRole(@CookieValue(value = "user", required = false) String userCookie, Model model) {


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
