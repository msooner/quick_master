package com.ron.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户实体
 *
 * @auther Ron
 * @date 2019/11/7
 */
public class SystemUser implements Serializable {

    private Integer id;
    private String password;
    private String username;
    private String email;
    private String createdBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdDate;

    private String lastModifiedBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastModifiedDate;

    private String orgCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date credentialExpiredDate;

    private Byte isLocked;
    private String roleNames;
    private String roleIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Date getCredentialExpiredDate() {
        return credentialExpiredDate;
    }

    public void setCredentialExpiredDate(Date credentialExpiredDate) {
        this.credentialExpiredDate = credentialExpiredDate;
    }

    public Byte getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Byte isLocked) {
        this.isLocked = isLocked;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", orgCode='" + orgCode + '\'' +
                ", credentialExpiredDate=" + credentialExpiredDate +
                ", isLocked=" + isLocked +
                ", roleNames='" + roleNames + '\'' +
                ", roleIds='" + roleIds + '\'' +
                '}';
    }

    public SystemUser() {}

    public SystemUser(Integer id, String username, String password, String createdBy, String email, Byte isLocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdBy = createdBy;
        this.email = email;
        this.isLocked = isLocked;
    }

    public SystemUser(Integer id, String password, String username, String createdBy, Date createdDate, String lastModifiedBy, Date lastModifiedDate, String orgCode, Date credentialExpiredDate, Byte isLocked, String roleNames, String roleIds) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.orgCode = orgCode;
        this.credentialExpiredDate = credentialExpiredDate;
        this.isLocked = isLocked;
        this.roleNames = roleNames;
        this.roleIds = roleIds;
    }
}
