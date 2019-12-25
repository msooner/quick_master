package com.ron.entity;

import java.io.Serializable;
import java.util.Date;

public class SystemUserRole implements Serializable {

    private Integer id;
    private String roleName;
    private Integer parentId;
    private String moduleIds;
    private String createBy;
    private Date createTime;

    public String getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public SystemUserRole(Integer id, String roleName, int parentId, String createBy) {
        this.id = id;
        this.roleName = roleName;
        this.parentId = parentId;
        this.createBy = createBy;
    }

    public SystemUserRole(Integer id, String roleName, String moduleIds, int parentId, String createBy) {
        this.id = id;
        this.moduleIds = moduleIds;
        this.roleName = roleName;
        this.parentId = parentId;
        this.createBy = createBy;
    }

    public SystemUserRole() {
    }

    @Override
    public String toString() {
        return "SystemUserRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", parentId=" + parentId +
                ", moduleIds='" + moduleIds + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
