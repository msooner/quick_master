package com.ron.controller;

import com.ron.common.constants.DigitConstant;
import com.ron.common.constants.StringConsant;
import com.ron.dto.ResponseResult;
import com.ron.entity.SystemUser;
import com.ron.service.SystemUserService;
import com.ron.utils.CookieUtil;
import com.ron.utils.StringUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 登录逻辑
 *
 * @author Ron
 * @date 2019/11/6
 */
@Controller
@RequestMapping
public class LoginController {

    @Autowired
    SystemUserService systemUserService;

    /**
     * 用户登录界面(默认为登录界面，如果已经登录则跳转至用户中心)
     *
     * @return String
     */
    @GetMapping({"/", "/login"})
    public String login(@CookieValue(name = "user", defaultValue = "") String userCookie) {
        //如果用户Cookie不存在，则生成
        if ("".equals(userCookie) || userCookie == null) {
            String randomString = StringUtil.getRandomString(10);
            String userMD5Cookie = StringUtil.getMD5String(randomString, "");
            CookieUtil.setCookie("user", userMD5Cookie);
        } else {
            //如果用户已登录，则跳转至后台首页
            SystemUser systemUser = systemUserService.getUserInfo(userCookie);
            if (systemUser != null) {
                return "page/index";
            }
        }

        return "page/login";
    }

    /**
     * 登录逻辑
     *
     * @param username
     * @param rememberMe
     * @param password
     * @param userCookie
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/loginResult")
    public ResponseResult loginResult(@RequestParam(value = "username", required = true) String username,
                                               @RequestParam(value = "rememberMe", defaultValue = "1") Integer rememberMe,
                                               @RequestParam(value = "password", required = true) String password,
                                               @CookieValue(name = "user", defaultValue = "") String userCookie) {
        //验证用户名或密码
        if (StringUtil.isSqlInjection(username) || StringUtil.isSqlInjection(password)) {
            return new ResponseResult(DigitConstant.USERNAME_OR_PASSWORD_INJECTION, "", StringConsant.USERNAME_OR_PASSWORD_INJECTION);
        }
        //判断用户是否已经登录
        if (systemUserService.checkUserIsLogged(userCookie)) {
            return new ResponseResult(DigitConstant.ALREADY_LOGGED_ERROR, "", StringConsant.ALREADY_LOGGED_ERROR);
        }
        //加密密码
        String MD5Password = StringUtil.getMD5String(password, StringConsant.PASSWORD_SALT);
        //验证用户账户信息
        SystemUser sysUser = systemUserService.getLoginUser(username, MD5Password);
        if (sysUser == null) {
            return new ResponseResult(DigitConstant.USER_NAME_NOT_EXIST, "", StringConsant.USER_NAME_NOT_EXIST);
        }

        //缓存用户信息
        String userCacheKey = userCookie + String.valueOf(sysUser.getId());
        int cacheTime = DigitConstant.DEFAULT_CACHE_TIME;
        if (rememberMe == 1) {
            cacheTime = DigitConstant.REMEMBER_ME_CACHE_TIME;
        }
        systemUserService.setUserInfo(userCacheKey, sysUser, cacheTime);

        return new ResponseResult(DigitConstant.SUCCESS_LOGED, sysUser, StringConsant.SUCCESS_LOGED);
    }

    /**
     * 注册逻辑
     *
     * @param username
     * @param password
     * @param rePassword
     * @param email
     * @param userCookie
     * @return
     */
    @ResponseBody
    @PostMapping("/registerResult")
    public ResponseResult registerResult(@RequestParam(value = "username", required = true) String username,
                                         @RequestParam(value = "password", required = true) String password,
                                         @RequestParam(value = "rePassword", required = true) String rePassword,
                                         @RequestParam(value = "email", required = true) String email,
                                         @CookieValue("user") String userCookie) {

        //判断用户是否已经登录
        if (systemUserService.checkUserIsLogged(userCookie)) {
            return new ResponseResult(DigitConstant.ALREADY_LOGGED_ERROR, "", StringConsant.ALREADY_LOGGED_ERROR);
        }
        //判断密码
        if (! password.equals(rePassword)) {
            return new ResponseResult(DigitConstant.REPASSWORD_DIFFERENT, "", StringConsant.REPASSWORD_DIFFERENT);
        }
        //验证用户名或密码
        if (StringUtil.isSqlInjection(username) || StringUtil.isSqlInjection(password)) {
            return new ResponseResult(DigitConstant.USERNAME_OR_PASSWORD_INJECTION, "", StringConsant.USERNAME_OR_PASSWORD_INJECTION);
        }
        //验证用户名和密码是否相同
        if (username.equals(password)) {
            return new ResponseResult(DigitConstant.USERNAME_PASSWORD_SAME_ERROR, "", StringConsant.USERNAME_PASSWORD_SAME_ERROR);
        }
        //验证邮箱
        if (!StringUtil.isEmail(email) || StringUtil.isSqlInjection(email)) {
            return new ResponseResult(DigitConstant.EMAIL_ERROR, "", StringConsant.EMAIL_ERROR);
        }
        //判断账户信息是否存在
        if (systemUserService.checkRegisterSystemUser(username, email)) {
            return new ResponseResult(DigitConstant.USERNAME_ALREADLY_EXISTS, "", StringConsant.USERNAME_ALREADLY_EXISTS);
        }
        //用户注册
        String createBy = "System";
        //加密密码
        String MD5Password = StringUtil.getMD5String(password, StringConsant.PASSWORD_SALT);
        SystemUser systemUser = new SystemUser(null, username, MD5Password, createBy, email);
        systemUserService.registerUser(systemUser);
        int systemUserId = systemUser.getId();
        if (systemUserId <= 0) {
            return new ResponseResult(DigitConstant.REGISTER_FAIL, "", StringConsant.REGISTER_FAIL);
        }
        //获取用户信息
        SystemUser sUser = systemUserService.getUserById(systemUserId);
        if (sUser == null) {
            return new ResponseResult(DigitConstant.REGISTER_FAIL, "", StringConsant.REGISTER_FAIL);
        }
        //缓存用户信息
        String userCacheKey = userCookie + String.valueOf(sUser.getId());
        int cacheTime = DigitConstant.DEFAULT_CACHE_TIME;
        systemUserService.setUserInfo(userCacheKey, sUser, cacheTime);

        return new ResponseResult(DigitConstant.SUCCESS_LOGED, "", StringConsant.SUCCESS_LOGED);
    }

    /**
     * 发送找回密码邮件
     *
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendResetPasswordEmail")
    public ResponseResult sendResetPasswordEmail(@RequestParam(value = "username", required = true) String username,
                                                 @RequestParam(value = "email", required = true) String email) {
        //检测用户邮箱是否存在
        if (!systemUserService.checkRegisterSystemUser(username, email)) {
            return new ResponseResult(DigitConstant.ACCOUNT_NOEXISTS, "", StringConsant.ACCOUNT_NOEXISTS);
        }
        //发送重置密码邮件


        return new ResponseResult(DigitConstant.SEND_EMAIL_SUCCESS, "", StringConsant.SEND_EMAIL_SUCCESS);
    }
}
