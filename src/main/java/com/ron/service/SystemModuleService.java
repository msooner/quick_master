package com.ron.service;

import com.ron.entity.SystemModule;

import java.util.List;
import java.util.Map;

/**
 * 模块相关逻辑接口
 */
public interface SystemModuleService {

    /**
     * 查询所有模块信息
     *
     * @return List
     */
    List<SystemModule> getAllModuleList();

    /**
     * 查询一级模块信息
     *
     * @return List
     */
    List<SystemModule> getModuleList();

    /**
     * 查询二级模块信息
     *
     * @param parentId 父级模块ID
     * @return List
     */
    List<SystemModule> getChildModuleList(Integer parentId);

    /**
     * 查询某个模块信息
     *
     * @param moduleId
     * @return SystemModule
     */
    SystemModule getModuleInfo(Integer moduleId);

    /**
     * 根据模块列表，封装以parentId为键的Map
     *
     * @param systemModuleList
     * @return
     */
    Map<Integer, List<SystemModule>> getChildModuleMap(List<SystemModule> systemModuleList);

    /**
     * 添加模块信息
     *
     * @param systemModule 模块实体
     * @return boolean
     */
    boolean addModule(SystemModule systemModule);

    /**
     * 删除模块信息
     *
     * @param moduleId 模块ID
     * @return boolean
     */
    boolean deleteModule(Integer moduleId);

    /**
     * 删除下级模块信息
     *
     * @param parentId 上级模块id
     * @return boolean
     */
    boolean deleteChildModule(Integer parentId);

    /**
     * 编辑模块信息
     *
     * @param systemModule 模块实体
     * @return boolean
     */
    boolean editModule(SystemModule systemModule);

    /**
     * 检测模块是否已经存在
     *
     * @param moduleName 模块名称
     * @param parentId  上级模块ID
     * @return boolean
     */
    boolean checkModuleIsExists(String moduleName, Integer parentId);
}
