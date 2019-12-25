package com.ron.service.impl;

import com.ron.entity.SystemUserRole;
import com.ron.mapper.SystemUserRoleMapper;
import com.ron.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther Ron
 * @date 2019/10/6
 */
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemUserRoleMapper systemUserRoleMapper;

    /**
     * 查询一级部门信息
     *
     * @return List
     */
    @Override
    public List<SystemUserRole> getAllRoleList() {
        return systemUserRoleMapper.getAllRoleList();
    }

    /**
     * 查询一级部门信息
     *
     * @return List
     */
    @Override
    public List<SystemUserRole> getRoleList() {
        return systemUserRoleMapper.getRoleList();
    }

    /**
     * 查询二级部门信息
     *
     * @param parentId 父级部门ID
     * @return List
     */
    @Override
    public List<SystemUserRole> getChildRoleList(Integer parentId) {
        if (parentId == null || parentId < 0) {
            return new ArrayList<>();
        }

        return systemUserRoleMapper.getChildRoleList(parentId);
    }

    /**
     * 查询所有二级角色信息
     *
     * @return List
     */
    @Override
    public List<SystemUserRole> getAllChildRoleList() {
        return systemUserRoleMapper.getAllChildRoleList();
    }

    /**
     * 查询某个部门信息
     *
     * @param roleId
     * @return SystemUserRole
     */
    @Override
    public SystemUserRole getRoleInfo(int roleId) {
        if (roleId <= 0) {
            return null;
        }
        return systemUserRoleMapper.getRole(roleId);
    }

    /**
     * 添加部门信息
     *
     * @param systemUserRole 部门实体
     * @return boolean
     */
    @Override
    public boolean addRole(SystemUserRole systemUserRole) {
        if (StringUtils.isEmpty(systemUserRole.getRoleName()) || StringUtils.isEmpty(systemUserRole.getParentId())
                || StringUtils.isEmpty(systemUserRole.getParentId())) {
            return false;
        }
        int addResult = systemUserRoleMapper.addRole(systemUserRole);
        if (addResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 删除角色信息
     *
     * @param roleId 部门ID
     * @return boolean
     */
    @Override
    public boolean deleteRole(Integer roleId) {
        if (roleId == null || roleId <= 0) {
            return false;
        }
        int deleteResult = systemUserRoleMapper.deleteRole(roleId);
        if (deleteResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 删除下级角色信息
     *
     * @param parentId 上级部门id
     * @return boolean
     */
    @Override
    public boolean deleteChildRole(Integer parentId) {
        if (parentId == null || parentId <= 0) {
            return false;
        }
        int deleteResult = systemUserRoleMapper.deleteChildRole(parentId);
        if (deleteResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 编辑角色信息
     *
     * @param systemUserRole 角色实体
     * @return boolean
     */
    @Override
    public boolean editRole(SystemUserRole systemUserRole) {
        if (systemUserRole == null || systemUserRole.getRoleName() == null || systemUserRole.getParentId() == null) {
            return false;
        }
        int updateResult = systemUserRoleMapper.editRole(systemUserRole);
        if (updateResult <= 0) {
            return false;
        }

        return true;
    }

    /**
     * 角色授权
     *
     * @param systemUserRole 角色实体
     * @return boolean
     */
    @Override
    public boolean authorizationRole(SystemUserRole systemUserRole) {
        if (systemUserRole.getParentId() == null || systemUserRole.getParentId() < 0 || systemUserRole.getId() == null || systemUserRole.getId() <= 0) {
            return false;
        }
        if (StringUtils.isEmpty(systemUserRole.getModuleIds()) || StringUtils.isEmpty(systemUserRole.getCreateBy())) {
            return false;
        }
        int result = systemUserRoleMapper.authorizationRole(systemUserRole);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 检测角色是否已经存在
     *
     * @param roleName 角色名称
     * @param parentId       上级角色ID
     * @return boolean
     */
    @Override
    public boolean checkRoleIsExists(String roleName, Integer parentId) {
        if (StringUtils.isEmpty(roleName) || parentId == null || parentId < 0) {
            return false;
        }
        SystemUserRole systemUserRole = systemUserRoleMapper.checkRole(roleName, parentId);
        if (systemUserRole != null && systemUserRole.getId() > 0) {
            return true;
        }
        return false;
    }
}
