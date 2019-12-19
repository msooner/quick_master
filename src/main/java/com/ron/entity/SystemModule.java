package com.ron.entity;

import java.io.Serializable;
import java.util.Date;

public class SystemModule implements Serializable {

    private Integer id;
    private String moduleName;
    private String moduleUrl;
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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
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

    public SystemModule(Integer id, String moduleName, String moduleUrl, Integer parentId, String createBy) {
        this.id = id;
        this.moduleName = moduleName;
        this.moduleUrl = moduleUrl;
        this.parentId = parentId;
        this.createBy = createBy;
    }

    public SystemModule() {
    }

    @Override
    public String toString() {
        return "SystemModule{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", moduleUrl='" + moduleUrl + '\'' +
                ", parentId=" + parentId +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
