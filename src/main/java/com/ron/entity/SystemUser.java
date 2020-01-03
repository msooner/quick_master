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

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdDate;

    private String lastModifiedBy;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastModifiedDate;

    private Integer deptId;
    private SystemUserDepartment department;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date credentialExpiredDate;

    private  Byte isLocked;
    private Integer roleId;
    private SystemUserRole role;

    public SystemUserDepartment getDepartment() {
        return department;
    }

    public void setDepartment(SystemUserDepartment department) {
        this.department = department;
    }

    public SystemUserRole getRole() {
        return role;
    }

    public void setRole(SystemUserRole role) {
        this.role = role;
    }

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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", deptId=" + deptId +
                ", department=" + department +
                ", credentialExpiredDate=" + credentialExpiredDate +
                ", isLocked=" + isLocked +
                ", roleId=" + roleId +
                ", role=" + role +
                '}';
    }

    public SystemUser(Integer id, String password, String username, String email, String createdBy, Date createdDate, String lastModifiedBy, Date lastModifiedDate, Integer deptId, SystemUserDepartment department, Date credentialExpiredDate, Byte isLocked, Integer roleId, SystemUserRole role) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.deptId = deptId;
        this.department = department;
        this.credentialExpiredDate = credentialExpiredDate;
        this.isLocked = isLocked;
        this.roleId = roleId;
        this.role = role;
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

    public SystemUser(Integer id, String password, String username, String createdBy, Date createdDate, String lastModifiedBy, Date lastModifiedDate, Integer deptId, Date credentialExpiredDate, Byte isLocked, Integer roleId, SystemUserRole role, SystemUserDepartment department) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.deptId = deptId;
        this.credentialExpiredDate = credentialExpiredDate;
        this.isLocked = isLocked;
        this.roleId = roleId;
        this.role = role;
        this.department = department;
    }
}
