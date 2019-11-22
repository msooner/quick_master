package com.ron.service;

import com.ron.entity.SystemUser;
import org.joda.time.DateTime;

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
     * 从redis获取系统用户信息
     *
     * @return
     */
    SystemUser getUserInfo(String userCookie);

    void setUserInfo(String userCacheKey, SystemUser systemUser, int cacheTime);

    /**
     * 查询所有用户信息
     */
    List<SystemUser> getAllUsers();

    /**
     * 查询某个用户信息
     *
     * @param userId
     * @return
     */
    SystemUser getUserById(int userId);

    /**
     * 获取登录用户信息
     *
     * @param username
     * @param password
     * @return
     */
    SystemUser getLoginUser(String username, String password);

    /**
     * 用户注册
     *
     * @return
     */
    void registerUser(SystemUser systemUser);

    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    void addUser(SystemUser user);

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    void deleteUser(Integer userId);

    /**
     * 编辑用户信息
     *
     * @param user
     * @return
     */
    void editUser(SystemUser user);

    boolean checkUserIsLogged(String userCookie);

    /**
     * 检测用户是否已经存在
     *
     * @param username
     * @param email
     * @return
     */
    boolean checkRegisterSystemUser(String username, String email);
}
