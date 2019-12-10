package com.ron.entity;

import java.io.Serializable;

public class SystemUserDepartment implements Serializable{

    private Integer id;
    private String departmentName;
    private Integer parentId;
    private String createBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public SystemUserDepartment(Integer id, String departmentName, Integer parentId, String createBy) {
        this.id = id;
        this.departmentName = departmentName;
        this.parentId = parentId;
        this.createBy = createBy;
    }

    public SystemUserDepartment() {}

    @Override
    public String toString() {
        return "SystemUserDepartment{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", parentId=" + parentId +
                ", createBy='" + createBy + '\'' +
                '}';
    }
}
