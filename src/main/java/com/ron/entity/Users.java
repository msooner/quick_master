package com.ron.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户实体
 */
public class Users implements Serializable{

    private Integer userId;
    private String email;
    private String userName;
    private String nickName;
    private String avatar;
    private Byte sex;
    private Integer birthday;
    private String mobilePhone;
    private BigDecimal consumptionCount;
    private Short goodsComment;
    private Integer addressId;
    private Short groupId;
    private Integer updateTime;
    private Byte isFirstOrder;
    private BigDecimal balance;
    private Integer integral;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public BigDecimal getConsumptionCount() {
        return consumptionCount;
    }

    public void setConsumptionCount(BigDecimal consumptionCount) {
        this.consumptionCount = consumptionCount;
    }

    public Short getGoodsComment() {
        return goodsComment;
    }

    public void setGoodsComment(Short goodsComment) {
        this.goodsComment = goodsComment;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Short getGroupId() {
        return groupId;
    }

    public void setGroupId(Short groupId) {
        this.groupId = groupId;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsFirstOrder() {
        return isFirstOrder;
    }

    public void setIsFirstOrder(Byte isFirstOrder) {
        this.isFirstOrder = isFirstOrder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    @Override
    public String toString() {
        return "Users{" +
                "UserId=" + userId +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", consumptionCount=" + consumptionCount +
                ", goodsComment=" + goodsComment +
                ", addressId=" + addressId +
                ", groupId=" + groupId +
                ", updateTime=" + updateTime +
                ", isFirstOrder=" + isFirstOrder +
                ", balance=" + balance +
                ", integral=" + integral +
                '}';
    }
}
