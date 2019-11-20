package com.ron.mapper;

import com.ron.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * @param userId
     */
    SystemUser getUser(int userId);

    /**
     *  查询所有用户信息
     */
    List<SystemUser> getUsers();

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    SystemUser getLoginUser(@Param("username") String username, @Param("password") String password);

    /**
     * 用户注册
     */
    //void registerUser(@Param("username") String username, @Param("password") String password, @Param("email") String email);
    void registerUser(SystemUser systemUser);

    /**
     * 获取某个部门所有用户
     */
    List<SystemUser> getDepartmentUsers();
}
