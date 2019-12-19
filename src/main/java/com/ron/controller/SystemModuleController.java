package com.ron.controller;

import com.ron.common.constants.DigitConstant;
import com.ron.common.constants.StringConsant;
import com.ron.dto.ResponseResult;
import com.ron.entity.SystemUser;
import com.ron.entity.SystemModule;
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

/**
 * 系统模块控制类
 *
 * @author Ron 2019/12/17
 */
@Controller
@RequestMapping
public class SystemModuleController {

    @Autowired
    SystemModuleService systemModuleService;

    @Autowired
    SystemUserService systemUserService;

    /**
     * 模块管理
     *
     * @param model 页面model
     * @return String
     */
    @RequestMapping("/admin/module-manager")
    public String moduleManager(Model model) {
        List<SystemModule> systemModules = systemModuleService.getAllModuleList();
        model.addAttribute("systemModules", systemModules);

        return "page/module/module-index";
    }

    /**
     * 添加模块页面
     *
     * @param userCookie 用户cookie
     * @param model view model
     * @return String
     */
    @RequestMapping("/admin/add-module")
    public String addModule(@CookieValue(value = "user", required = false) String userCookie, Model model) {
        if (userCookie == null) {
            return "redirect:/login";
        }
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //获取所有的角色信息
        List<SystemModule> modules = systemModuleService.getModuleList();
        model.addAttribute("systemUser", systemUser);
        model.addAttribute("systemModules", modules);

        return "page/module/module-add";
    }

    /**
     * 编辑模块页面
     *
     * @param userCookie 用户cookie
     * @param model view model
     * @return String
     */
    @RequestMapping("/admin/edit-module")
    public String editModule(@CookieValue(value = "user", required = false) String userCookie,
                             @RequestParam("moduleId") Integer moduleId, Model model) {
        if (userCookie == null || "".equals(userCookie)) {
            return "redirect:/login";
        }
        if (moduleId == null || moduleId <= 0) {
            return "redirect:/admin/module-manager";
        }
        //获取用户信息
        SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
        //获取当前模块信息
        SystemModule systemModule = systemModuleService.getModuleInfo(moduleId);
        if (systemModule == null) {
            return "redirect:/admin/module-manager";
        }
        //获取所有的角色信息
        List<SystemModule> modules = systemModuleService.getModuleList();
        model.addAttribute("systemUser", systemUser);
        model.addAttribute("systemModules", modules);
        model.addAttribute("systemModule", systemModule);

        return "page/module/module-edit";
    }

    /**
     * 添加模块操作
     *
     * @param systemModule 模块实体
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/add-module-result")
    public ResponseResult addModuleResult(SystemModule systemModule) {
        //检测角色信息
        if (systemModule.getParentId() == null || systemModule.getParentId() < 0 || StringUtils.isEmpty(systemModule.getModuleUrl())) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        //检测模块信息是否存在
        if (systemModuleService.checkModuleIsExists(systemModule.getModuleName(), systemModule.getParentId())) {
            return new ResponseResult(DigitConstant.MODULE_INFO_IS_EXISTS, "", StringConsant.MODULE_INFO_IS_EXISTS);
        }
        //添加模块信息
        if (! systemModuleService.addModule(systemModule)) {
            return new ResponseResult(DigitConstant.ADD_MODULE_FAIL, "", StringConsant.ADD_MODULE_FAIL);
        }

        return new ResponseResult(DigitConstant.ADD_MODULE_SUCCESS, "", StringConsant.ADD_MODULE_SUCCESS);
    }

    /**
     * 编辑模块操作
     *
     * @param systemModule 模块实体
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/edit-module-result")
    public ResponseResult editModuleResult(SystemModule systemModule) {
        //检测角色信息
        if (systemModule.getParentId() == null || systemModule.getParentId() < 0 || StringUtils.isEmpty(systemModule.getModuleUrl())) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        //检测模块信息是否存在
        if (systemModuleService.getModuleInfo(systemModule.getId()) == null) {
            return new ResponseResult(DigitConstant.MODULE_INFO_NOT_EXISTS, "", StringConsant.MODULE_INFO_NOT_EXISTS);
        }
        //编译模块信息
        if (! systemModuleService.editModule(systemModule)) {
            return new ResponseResult(DigitConstant.ADD_MODULE_FAIL, "", StringConsant.ADD_MODULE_FAIL);
        }

        return new ResponseResult(DigitConstant.ADD_MODULE_SUCCESS, "", StringConsant.ADD_MODULE_SUCCESS);
    }

    /**
     * 获取一级模块下的子模块列表
     *
     * @param parentId 上级模块
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/get-child-module-list")
    public ResponseResult getChildModuleList(@RequestParam("parentId") Integer parentId) {
        if (parentId == null || parentId < 0) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        List<SystemModule> moduleList = systemModuleService.getChildModuleList(parentId);

        return new ResponseResult(DigitConstant.GET_MODULE_LIST_SUCCESS, moduleList, StringConsant.GET_MODULE_LIST_SUCCESS);
    }

    /**
     * 删除模块信息
     *
     * @param id 模块ID
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/admin/delete-module")
    public ResponseResult deleteRole(@RequestParam("id") Integer id) {
        //检测传入参数
        if (id == null || id <= 0) {
            return new ResponseResult(DigitConstant.REQUEST_PARAMETER_ERROR, "", StringConsant.REQUEST_PARAMETER_ERROR);
        }
        //删除用户模块
        if (! systemModuleService.deleteModule(id)) {
            return new ResponseResult(DigitConstant.DELETE_MODULE_FAIL, "", StringConsant.DELETE_MODULE_FAIL);
        }
        //如果有子模块，同步删除
        List<SystemModule> moduleList = systemModuleService.getChildModuleList(id);
        if (moduleList.size() > 0) {
            systemModuleService.deleteChildModule(id);
        }

        return new ResponseResult(DigitConstant.DELETE_MODULE_SUCCESS, "", StringConsant.DELETE_MODULE_SUCCESS);
    }
}
