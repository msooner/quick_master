package com.ron.mapper;

import com.ron.entity.SystemUser;
import com.ron.entity.SystemUserDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther Ron
 * @data 2019/12/09
 */
@Mapper
public interface SystemDepartmentMapper {

    /**
     * 添加部门信息
     *
     * @param systemUserDepartment 部门实体
     */
    int addDepartment(SystemUserDepartment systemUserDepartment);

    /**
     * 编辑部门信息
     *
     * @param systemUserDepartment 部门实体
     */
    int editDepartment(SystemUserDepartment systemUserDepartment);

    /**
     * 删除部门贪睡
     *
     * @param departmentId 部门ID
     */
    int deleteDepartment(Integer departmentId);

    /**
     * 查询某个部门信息
     *
     * @param departmentId
     * @return SystemUserDepartment
     */
    SystemUserDepartment getDepartment(Integer departmentId);

    /**
     *  查询所有部门信息
     *
     *  @return List
     */
    List<SystemUserDepartment> getDepartmentList();

    /**
     * 检测部门是否存在
     *
     * @param departmentName 部门名称
     * @param parentId 上级部门ID
     * @return
     */
    SystemUserDepartment checkDepartment(@Param("departmentName") String departmentName, @Param("parentId") Integer parentId);

    /**
     * 获取某个部门所有用户
     *
     * @return List
     */
    List<SystemUser> getDepartmentUsers();
}
