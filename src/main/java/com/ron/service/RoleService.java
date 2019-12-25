package com.ron.service;

import com.ron.entity.SystemUserRole;

import java.util.List;

/**
 * 角色相关逻辑接口
 */
public interface RoleService {

    /**
     * 查询所有角色信息
     *
     * @return List
     */
    List<SystemUserRole> getAllRoleList();

    /**
     * 查询一级角色信息
     *
     * @return List
     */
    List<SystemUserRole> getRoleList();

    /**
     * 查询二级角色信息
     *
     * @param parentId 父级角色ID
     * @return List
     */
    List<SystemUserRole> getChildRoleList(Integer parentId);

    /**
     * 查询所有二级角色信息
     *
     * @return List
     */
    List<SystemUserRole> getAllChildRoleList();

    /**
     * 查询某个角色信息
     *
     * @param roleId
     * @return SystemUserRole
     */
    SystemUserRole getRoleInfo(int roleId);

    /**
     * 添加角色信息
     *
     * @param systemUserRole 角色实体
     * @return boolean
     */
    boolean addRole(SystemUserRole systemUserRole);

    /**
     * 删除角色信息
     *
     * @param roleId 角色ID
     * @return boolean
     */
    boolean deleteRole(Integer roleId);

    /**
     * 删除下级角色信息
     *
     * @param parentId 上级角色id
     * @return boolean
     */
    boolean deleteChildRole(Integer parentId);

    /**
     * 编辑角色信息
     *
     * @param systemUserRole 角色实体
     * @return boolean
     */
    boolean editRole(SystemUserRole systemUserRole);

    /**
     * 角色授权
     *
     * @param systemUserRole 角色实体
     * @return boolean
     */
    boolean authorizationRole(SystemUserRole systemUserRole);

    /**
     * 检测角色是否已经存在
     *
     * @param roleName 角色名称
     * @param parentId  上级角色ID
     * @return boolean
     */
    boolean checkRoleIsExists(String roleName, Integer parentId);
}
