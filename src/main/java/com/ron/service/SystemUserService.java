package com.ron.service;

import com.ron.entity.SystemUser;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户登录相关逻辑接口
 */
public interface SystemUserService {

    /**
     * 从redis获取系统用户信息
     *
     * @return Object
     */
    Object getUserInfo(String userCookie);

    /**
     * 缓存用户信息
     *
     * @param userCacheKey 缓存key
     * @param systemUser 用户实体
     * @param cacheTime 缓存时间
     */
    void setUserInfo(String userCacheKey, SystemUser systemUser, int cacheTime);

    /**
     * 查询所有用户信息
     */
    List<SystemUser> getAllUsers();

    /**
     * 级联查询所有的管理员列表
     *
     * @return List
     */
    List<SystemUser> getSystemUserList();

    /**
     * 查询某个用户信息
     *
     * @param userId 用户id
     * @return SystemUser
     */
    SystemUser getUserById(int userId);

    /**
     * 根据用户名称获取用户信息
     *
     * @param username 用户名
     * @return SystemUser
     */
    SystemUser getUserByName(String username);

    /**
     * 获取登录用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return SystemUser
     */
    SystemUser getLoginUser(String username, String password);

    /**
     * 用户注册
     */
    void registerUser(SystemUser systemUser);

    /**
     * 添加用户信息
     *
     * @param user 用户实体
     */
    boolean addUser(SystemUser user);

    /**
     * 检测需要存储的用户信息是否合法
     *
     * @param user 用户实体
     * @return
     */
    boolean checkSaveUser(SystemUser user);

    /**
     * 删除用户信息
     *
     * @param userId 用户id
     */
    int deleteUser(Integer userId);

    /**
     * 编辑用户信息
     *
     * @param user 用户实体
     * @return boolean
     */
    boolean editUser(SystemUser user);

    /**
     * 检测用户是否已经登录
     *
     * @param userCookie 用户cookie
     * @return boolean
     */
    boolean checkUserIsLogged(String userCookie);

    /**
     * 检查非法用户跳转至目标页
     *
     * @param response HttpServletResponse
     * @param redirectUrl 跳转url
     */
    String redirectSystemUser(HttpServletResponse response, String redirectUrl, String userCookie) throws Exception;

    /**
     * 检测用户是否已经存在
     *
     * @param username 用户名
     * @param email 邮箱
     * @return boolean
     */
    boolean checkRegisterSystemUser(String username, String email);

    /**
     * 找回密码更新
     *
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     * @return boolean
     */
    boolean updateForgotPassword(String username, String email, String password);

    /**
     * 用户登出
     *
     * @param userCookie 用户cookie
     */
    void logout(String userCookie);
}
