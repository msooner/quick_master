package com.ron.service.impl;

import com.ron.entity.SystemModule;
import com.ron.mapper.SystemModuleMapper;
import com.ron.service.SystemModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther Ron
 * @date 2019/10/6
 */
@Service
public class SystemModuleServiceImpl implements SystemModuleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemModuleMapper systemModuleMapper;

    /**
     * 查询一级模块信息
     *
     * @return List
     */
    @Override
    public List<SystemModule> getAllModuleList() {
        return systemModuleMapper.getAllModuleList();
    }

    /**
     * 查询一级模块信息
     *
     * @return List
     */
    @Override
    public List<SystemModule> getModuleList() {
        return systemModuleMapper.getModuleList();
    }

    /**
     * 查询二级模块信息
     *
     * @param parentId 父级模块ID
     * @return List
     */
    @Override
    public List<SystemModule> getChildModuleList(Integer parentId) {
        if (parentId == null || parentId < 0) {
            return new ArrayList<>();
        }

        return systemModuleMapper.getChildModuleList(parentId);
    }

    /**
     * 查询某个模块信息
     *
     * @param moduleId 模块id
     * @return SystemModule
     */
    @Override
    public SystemModule getModuleInfo(Integer moduleId) {
        if (moduleId == null || moduleId <= 0) {
            return null;
        }
        return systemModuleMapper.getModule(moduleId);
    }

    /**
     * 根据模块列表，封装以parentId为键的Map
     *
     * @param systemModuleList 模块列表
     * @return Map
     */
    @Override
    public Map<Integer, List<SystemModule>> getChildModuleMap(List<SystemModule> systemModuleList) {
        Map<Integer, List<SystemModule>> moduleListMap = new HashMap<>();
        if (systemModuleList != null && systemModuleList.size() > 0) {
            for (int i = 0; i < systemModuleList.size(); i++) {
                List<SystemModule> tmpList = new ArrayList<>();
                for (SystemModule systemModule : systemModuleList) {
                    if (systemModule.getParentId() > 0 && ! moduleListMap.containsKey(systemModule.getParentId())) {
                        tmpList.add(systemModule);
                    }

                }
                SystemModule sm = systemModuleList.get(i);
                if (tmpList.size() > 0 && sm.getParentId() > 0) {
                    moduleListMap.put(sm.getParentId(), tmpList);
                }
            }

        }
        return moduleListMap;
    }

    /**
     * 添加模块信息
     *
     * @param systemModule 模块实体
     * @return boolean
     */
    @Override
    public boolean addModule(SystemModule systemModule) {
        if (StringUtils.isEmpty(systemModule.getModuleName()) || StringUtils.isEmpty(systemModule.getParentId())
                || StringUtils.isEmpty(systemModule.getParentId())) {
            return false;
        }
        int addResult = systemModuleMapper.addModule(systemModule);
        if (addResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 删除模块信息
     *
     * @param moduleId 模块ID
     * @return boolean
     */
    @Override
    public boolean deleteModule(Integer moduleId) {
        if (moduleId == null || moduleId <= 0) {
            return false;
        }
        int deleteResult = systemModuleMapper.deleteModule(moduleId);
        if (deleteResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 删除下级模块信息
     *
     * @param parentId 上级模块id
     * @return boolean
     */
    @Override
    public boolean deleteChildModule(Integer parentId) {
        if (parentId == null || parentId <= 0) {
            return false;
        }
        int deleteResult = systemModuleMapper.deleteChildModule(parentId);
        if (deleteResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 编辑模块信息
     *
     * @param systemModule 模块实体
     * @return boolean
     */
    @Override
    public boolean editModule(SystemModule systemModule) {
        if (systemModule == null || StringUtils.isEmpty(systemModule.getModuleName()) ||
                systemModule.getParentId() == null || systemModule.getParentId() < 0) {
            return false;
        }
        int updateResult = systemModuleMapper.editModule(systemModule);
        if (updateResult <= 0) {
            return false;
        }

        return true;
    }

    /**
     * 检测模块是否已经存在
     *
     * @param moduleName 模块名称
     * @param parentId  上级模块ID
     * @return boolean
     */
    @Override
    public boolean checkModuleIsExists(String moduleName, Integer parentId) {
        if (StringUtils.isEmpty(moduleName) || parentId == null || parentId < 0) {
            return false;
        }
        SystemModule systemModule = systemModuleMapper.checkModule(moduleName, parentId);
        if (systemModule != null && systemModule.getId() > 0) {
            return true;
        }
        return false;
    }
}
