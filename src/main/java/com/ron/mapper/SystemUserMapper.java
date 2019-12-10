package com.ron.mapper;

import com.ron.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @auther Ron
 * @data 2019/11/05
 */
@Mapper
public interface SystemUserMapper {

    /**
     * 查询某个用户信息
     *
     * @param userId 用户ID
     */
    SystemUser getUser(@Param("userId") Integer userId);

    /**
     *  查询所有用户信息
     *
     *  @return List
     */
    List<SystemUser> getUsers();

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return SystemUser
     */
    SystemUser getLoginUser(@Param("username") String username, @Param("password") String password);

    /**
     * 用户注册
     *
     * @param systemUser 用户实体
     */
    void registerUser(SystemUser systemUser);

    /**
     * 检测账户是否存在
     *
     * @param username 用户名
     * @param email 邮件
     * @return System
     */
    SystemUser checkRegisterSystemUser(@Param("username") String username, @Param("email") String email);

    /**
     * 获取某个部门所有用户
     *
     * @return List
     */
    List<SystemUser> getDepartmentUsers();

    /**
     * 用户重置密码
     *
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     * @return boolean
     */
    boolean updateForgotPassword(@Param("username") String username, @Param("email") String email, @Param("password") String password);

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     * @return int
     */
    int deleteUser(Integer userId);
}
