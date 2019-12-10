package com.ron.entity;

import java.io.Serializable;

public class SystemUserRole implements Serializable {

    private Integer id;
    private String roleName;
    private int parentId;
    private String createBy;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public SystemUserRole() {
    }

    @Override
    public String toString() {
        return "SystemUserRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", parentId=" + parentId +
                ", createBy='" + createBy + '\'' +
                '}';
    }
}
