package com.ron.service;

import com.ron.entity.SystemUserDepartment;

import java.util.List;

/**
 * 部门相关逻辑接口
 */
public interface DepartmentService {

    /**
     * 业务接口：应该站在"使用者"的角度设计，比如：
     * 1.定义方法的颗粒度要细
     * 2.方法的参数要明确且简练，不建议用类似Map这种让使用者封装一堆参数传递过来
     * 3.方法的return类型，除了要明确返回值类型，还应该指定该方法可能抛出的异常
     */

    /**
     * 查询所有部门信息
     *
     * @return List
     */
    List<SystemUserDepartment> getDepartmentList();

    /**
     * 查询某个部门信息
     *
     * @param departmentId
     * @return SystemUserDepartment
     */
    SystemUserDepartment getDepartmentInfo(int departmentId);


    /**
     * 添加部门信息
     *
     * @param systemUserDepartment 部门实体
     * @return boolean
     */
    boolean addDepartment(SystemUserDepartment systemUserDepartment);

    /**
     * 删除部门信息
     *
     * @param departmentId 部门ID
     * @return boolean
     */
    boolean deleteDepartment(Integer departmentId);

    /**
     * 编辑部门信息
     *
     * @param systemUserDepartment 部门实体
     * @return boolean
     */
    boolean editDepartment(SystemUserDepartment systemUserDepartment);

    /**
     * 检测部门是否已经存在
     *
     * @param departmentName 部门名称
     * @param parentId  上线部门ID
     * @return boolean
     */
    boolean checkDepartmentIsExists(String departmentName, Integer parentId);
}
