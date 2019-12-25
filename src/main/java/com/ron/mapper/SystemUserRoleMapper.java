package com.ron.mapper;

import com.ron.entity.SystemUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther Ron
 * @data 2019/12/09
 */
@Mapper
public interface SystemUserRoleMapper {

    /**
     * 添加角色信息
     *
     * @param systemUserRole 角色实体
     * @return int
     */
    int addRole(SystemUserRole systemUserRole);

    /**
     * 编辑角色信息
     *
     * @param systemUserRole 角色实体
     * @return int
     */
    int editRole(SystemUserRole systemUserRole);

    /**
     * 角色授权
     *
     * @param systemUserRole 角色实体
     * @return int
     */
    int authorizationRole(SystemUserRole systemUserRole);

    /**
     * 删除角色信息
     *
     * @param roleId 角色ID
     * @return int
     */
    int deleteRole(Integer roleId);

    /**
     * 删除下级角色信息
     *
     * @param parentId 上级角色id
     * @return int
     */
    int deleteChildRole(@Param("parentId") Integer parentId);

    /**
     * 查询某个角色信息
     *
     * @param roleId
     * @return SystemUserRole
     */
    SystemUserRole getRole(Integer roleId);

    /**
     * 查询所有角色信息
     *
     * @return List
     */
    List<SystemUserRole> getAllRoleList();

    /**
     *  查询所有角色信息
     *
     *  @return List
     */
    List<SystemUserRole> getRoleList();

    /**
     * 查询角色的子角色
     *
     * @param parentId 父角色id
     * @return List
     */
    List<SystemUserRole> getChildRoleList(@Param("parentId") Integer parentId);

    /**
     * 查询所有子角色
     *
     * @return List
     */
    List<SystemUserRole> getAllChildRoleList();

    /**
     * 检测角色是否存在
     *
     * @param roleName 角色名称
     * @param parentId 上级角色ID
     * @return
     */
    SystemUserRole checkRole(@Param("roleName") String roleName, @Param("parentId") Integer parentId);

}
