package com.ron.service.impl;

import com.alibaba.fastjson.JSON;
import com.ron.common.constants.StringConsant;
import com.ron.entity.SystemUser;
import com.ron.mapper.SystemUserMapper;
import com.ron.service.SystemUserService;
import com.ron.utils.CookieUtil;
import com.ron.utils.RedisUtil;
import com.ron.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @auther Ron
 * @date 2019/10/6
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemUserMapper systemUsersMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 从redis缓存中获取用记信息
     *
     * @param userCookie 用户cookie
     * @return Object
     */
    @Override
    public Object getUserInfo(String userCookie) {
        if (StringUtils.isEmpty(userCookie)) {
            return null;
        }
        //从redis中获取用户信息
        Object o = redisUtil.get(userCookie);

        return o == null ? null : JSON.parseObject(o.toString(), SystemUser.class);
    }

    /**
     * 将用户信息存入redis缓存
     *
     * @param userCacheKey 缓存key
     * @param systemUser 用户实体
     * @param cacheTime 缓存时间
     */
    @Override
    public void setUserInfo(String userCacheKey, SystemUser systemUser, int cacheTime) {
        if (! StringUtils.isEmpty(userCacheKey) && systemUser.getId() > 0) {
            String jsonUser = JSON.toJSONString(systemUser);
            redisUtil.set(userCacheKey, jsonUser, cacheTime);
        }
    }

    /**
     * 查询所有用户信息
     *
     * @return List
     */
    @Override
    public List<SystemUser> getAllUsers() {
        return systemUsersMapper.getUsers();
    }

    /**
     * 级联查询所有的管理员列表
     *
     * @return List
     */
    @Override
    public List<SystemUser> getSystemUserList() {
        return systemUsersMapper.getSystemUserList();
    }

    /**
     * 查询用户信息
     *
     * @param userId 用户id
     * @return SystemUser
     */
    @Override
    public SystemUser getUserById(int userId) {
        return systemUsersMapper.getUser(userId);
    }

    /**
     * 根据用户名称获取用户信息
     *
     * @param username 用户名
     * @return SystemUser
     */
    @Override
    public SystemUser getUserByName(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        SystemUser systemUser = systemUsersMapper.getUserByName(username);

        return systemUser;
    }

    /**
     * 获取登录用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return SystemUser
     */
    @Override
    public SystemUser getLoginUser(String username, String password) {
        return systemUsersMapper.getLoginUser(username, password);
    }

    /**
     * 管理员注册
     *
     * @param systemUser 用户实体
     */
    @Override
    public void registerUser(SystemUser systemUser) {
        systemUsersMapper.registerUser(systemUser);
    }

    /**
     * 添加用户信息
     *
     * @param user 用户实体
     * @return boolean
     */
    @Override
    public boolean addUser(SystemUser user) {
        //检测用户信息
        if (user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getEmail())) {
            return false;
        }
        //检测角色和部门信息
        if (user.getRoleId() == null || user.getRoleId() <= 0 || user.getDeptId() == null || user.getDeptId() <= 0) {
            return false;
        }
        //检测锁定状态
        if (user.getIsLocked() == null || (user.getIsLocked() != 0 && user.getIsLocked() != 1)) {
            user.setIsLocked((byte) 1);
        }
        //密码加密
        String MD5Password = StringUtil.getMD5String(user.getPassword(), StringConsant.PASSWORD_SALT);
        user.setPassword(MD5Password);
        int addResult = systemUsersMapper.addSystemUser(user);
        if (addResult > 0) {
            return true;
        }

        return false;
    }

    /**
     * 检测用户
     *
     * @param user 用户实体
     * @return boolean
     */
    @Override
    public boolean checkSaveUser(SystemUser user) {
        if (user == null) {
            return false;
        }
        //检测用户名
        if (StringUtils.isEmpty(user.getUsername())) {
            return false;
        }
        //检测密码
        if (StringUtils.isEmpty(user.getPassword())) {
            return false;
        }

        return true;
    }

    /**
     * 删除用户信息
     *
     * @param userId 用户id
     * @return int
     */
    @Override
    public int deleteUser(Integer userId) {
        if (userId == null || userId <= 0) {
            return 0;
        }

        return systemUsersMapper.deleteUser(userId);
    }

    /**
     * 编辑用户信息
     *
     * @param user 用户实体
     * @return boolean
     */
    @Override
    public boolean editUser(SystemUser user) {
        //检测参数
        if (user == null) {
            return false;
        }
        int editResult = systemUsersMapper.editUser(user);
        if (editResult <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断用户是否已经登录
     *
     * @param userCookie 用户cookie
     * @return boolean
     */
    public boolean checkUserIsLogged(String userCookie) {
        if (StringUtils.isEmpty(userCookie)) {
            return false;
        }
        SystemUser systemUser = (SystemUser) this.getUserInfo(userCookie);
        if (systemUser != null && systemUser.getId() > 0) {
            return true;
        }

        return false;
    }

    /**
     * 检查非法用户跳转至目标页
     *
     * @param response    HttpServletResponse
     * @param redirectUrl 跳转url
     * @param userCookie 用户cookie
     */
    @Override
    public String redirectSystemUser(HttpServletResponse response, String redirectUrl, String userCookie) throws Exception {
        if (StringUtils.isEmpty(userCookie) || ! this.checkUserIsLogged(userCookie)) {
            redirectUrl = "/login";
            response.sendRedirect(redirectUrl);
        }

        return "";
    }

    /**
     * 检测用户是否已经存在
     *
     * @param username 用户名
     * @param email 邮箱
     * @return boolean
     */
    @Override
    public boolean checkRegisterSystemUser(String username, String email) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(email)) {
            return false;
        }
        SystemUser systemUser = systemUsersMapper.checkRegisterSystemUser(username, email);
        if (systemUser != null) {
            return true;
        }
        return false;
    }

    /**
     * 更新找回的密码
     *
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     * @return boolean
     */
    @Override
    public boolean updateForgotPassword(String username, String email, String password) {
        if (username.length() > 0 && email.length() > 0 && password.length() > 0) {
            return systemUsersMapper.updateForgotPassword(username, email, password);
        }

        return false;
    }

    /**
     * 登出用户
     *
     * @param userCookie 用户cookie
     */
    @Override
    public void logout(String userCookie) {
        if (! StringUtils.isEmpty(userCookie)) {
            //清除redis
            redisUtil.deleteKey(userCookie);
            //清除cookie
            CookieUtil.removeCookie(userCookie);
        }
    }

}
