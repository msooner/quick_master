package com.ron.service;

import com.ron.entity.SystemUser;

import java.util.List;

/**
 * 用户登录相关逻辑接口
 */
public interface SystemUserService {

    /**
     * 业务接口：应该站在"使用者"的角度设计，比如：
     * 1.定义方法的颗粒度要细
     * 2.方法的参数要明确且简练，不建议用类似Map这种让使用者封装一堆参数传递过来
     * 3.方法的return类型，除了要明确返回值类型，还应该指定该方法可能抛出的异常
     */

    /**
     * 查询所有用户信息
     */
    List<SystemUser> getAllUsers();

    /**
     * 查询某个用户信息
     */
    SystemUser getUserById(Integer userId);

    /**
     * 获取登录用户信息
     * @param userName
     * @param password
     * @return
     */
    SystemUser getLoginUser(String userName, String password);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    void addUser(SystemUser user);

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    void deleteUser(Integer userId);

    /**
     * 编辑用户信息
     * @param user
     * @return
     */
    void editUser(SystemUser user);
}
