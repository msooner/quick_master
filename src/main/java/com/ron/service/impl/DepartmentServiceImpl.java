package com.ron.service.impl;

import com.ron.entity.SystemUserDepartment;
import com.ron.mapper.SystemDepartmentMapper;
import com.ron.service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemDepartmentMapper systemDepartmentMapper;

    /**
     * 查询所有部门信息
     *
     * @return List
     */
    @Override
    public List<SystemUserDepartment> getDepartmentList() {
        return systemDepartmentMapper.getDepartmentList();
    }

    /**
     * 查询子部门
     *
     * @param parentId 父级部门ID
     * @return List
     */
    @Override
    public List<SystemUserDepartment> getChildDepartmentList(Integer parentId) {
        if (parentId == null || parentId < 0) {
            return new ArrayList<>();
        }

        return systemDepartmentMapper.getChildDepartmentList(parentId);
    }

    /**
     * 查询某个部门信息
     *
     * @param departmentId
     * @return SystemUserDepartment
     */
    @Override
    public SystemUserDepartment getDepartmentInfo(int departmentId) {
        if (departmentId <= 0) {
            return null;
        }
        return systemDepartmentMapper.getDepartment(departmentId);
    }

    /**
     * 添加部门信息
     *
     * @param systemUserDepartment 部门实体
     * @return boolean
     */
    @Override
    public boolean addDepartment(SystemUserDepartment systemUserDepartment) {
        if (StringUtils.isEmpty(systemUserDepartment.getDepartmentName()) || StringUtils.isEmpty(systemUserDepartment.getParentId())
                || StringUtils.isEmpty(systemUserDepartment.getParentId())) {
            return false;
        }
        int addResult = systemDepartmentMapper.addDepartment(systemUserDepartment);
        if (addResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 删除部门信息
     *
     * @param departmentId 部门ID
     * @return boolean
     */
    @Override
    public boolean deleteDepartment(Integer departmentId) {
        if (departmentId == null || departmentId <= 0) {
            return false;
        }
        int deleteResult = systemDepartmentMapper.deleteDepartment(departmentId);
        if (deleteResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 删除下级部门信息
     *
     * @param parentId 上级部门id
     * @return boolean
     */
    @Override
    public boolean deleteChildDepartment(Integer parentId) {
        if (parentId == null || parentId <= 0) {
            return false;
        }
        int deleteResult = systemDepartmentMapper.deleteChildDepartment(parentId);
        if (deleteResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 编辑部门信息
     *
     * @param systemUserDepartment 部门实体
     * @return boolean
     */
    @Override
    public boolean editDepartment(SystemUserDepartment systemUserDepartment) {
        if (systemUserDepartment == null || systemUserDepartment.getDepartmentName() == null || systemUserDepartment.getParentId() == null) {
            return false;
        }
        int updateResult = systemDepartmentMapper.editDepartment(systemUserDepartment);
        if (updateResult <= 0) {
            return false;
        }

        return true;
    }

    /**
     * 检测部门是否已经存在
     *
     * @param departmentName 部门名称
     * @param parentId       上线部门ID
     * @return boolean
     */
    @Override
    public boolean checkDepartmentIsExists(String departmentName, Integer parentId) {
        if (StringUtils.isEmpty(departmentName) || parentId == null || parentId < 0) {
            return false;
        }
        SystemUserDepartment systemUserDepartment = systemDepartmentMapper.checkDepartment(departmentName, parentId);
        if (systemUserDepartment != null && systemUserDepartment.getId() > 0) {
            return true;
        }
        return false;
    }
}
