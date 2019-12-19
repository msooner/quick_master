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
     * 添加部门信息
     *
     * @param systemUserRole 部门实体
     * @return int
     */
    int addRole(SystemUserRole systemUserRole);

    /**
     * 编辑部门信息
     *
     * @param systemUserRole 部门实体
     * @return int
     */
    int editRole(SystemUserRole systemUserRole);

    /**
     * 删除部门信息
     *
     * @param roleId 部门ID
     * @return int
     */
    int deleteRole(Integer roleId);

    /**
     * 删除下级部门信息
     *
     * @param parentId 上级部门id
     * @return int
     */
    int deleteChildRole(@Param("parentId") Integer parentId);

    /**
     * 查询某个部门信息
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
     *  查询所有部门信息
     *
     *  @return List
     */
    List<SystemUserRole> getRoleList();

    /**
     * 查询部门的子部门
     *
     * @param parentId 父部门id
     * @return List
     */
    List<SystemUserRole> getChildRoleList(@Param("parentId") Integer parentId);

    /**
     * 检测部门是否存在
     *
     * @param roleName 部门名称
     * @param parentId 上级部门ID
     * @return
     */
    SystemUserRole checkRole(@Param("roleName") String roleName, @Param("parentId") Integer parentId);

}
