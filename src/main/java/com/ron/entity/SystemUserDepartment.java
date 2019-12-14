package com.ron.entity;

import java.io.Serializable;
import java.util.Date;

public class SystemUserDepartment implements Serializable{

    private Integer id;
    private String departmentName;
    private Integer parentId;
    private String createBy;
    private Date createTime;
    private Date updateTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
