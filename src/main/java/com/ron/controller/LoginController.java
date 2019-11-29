package com.ron.controller;

import com.ron.common.constants.DigitConstant;
import com.ron.common.constants.StringConsant;
import com.ron.dto.ResponseResult;
import com.ron.entity.SystemUser;
import com.ron.service.MailService;
import com.ron.service.SystemUserService;
import com.ron.utils.CookieUtil;
import com.ron.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 登录逻辑
 *
 * @author Ron 2019/11/6
 */
@Controller
@RequestMapping
public class LoginController {

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    MailService mailService;

    @Value("${others.host}")
    private String host;

    /**
     * 用户登录界面(默认为登录界面，如果已经登录则跳转至用户中心)
     *
     * @param userCookie 用户cookie
     * @return string
     */
    @GetMapping({"/", "/login"})
    public String login(@CookieValue(name = "user", defaultValue = "") String userCookie) {
        //如果用户Cookie不存在，则生成
        if (StringUtils.isEmpty(userCookie)) {
            String randomString = StringUtil.getRandomString(10);
            String userMD5Cookie = StringUtil.getMD5String(randomString, "");
            CookieUtil.setCookie("user", userMD5Cookie);
        } else {
            //如果用户已登录，则跳转至后台首页
            SystemUser systemUser = (SystemUser) systemUserService.getUserInfo(userCookie);
            if (systemUser != null && systemUser.getId() > 0) {
                return "redirect:/admin";
            }
        }

        return "page/login";
    }

    /**
     * 登录逻辑
     *
     * @param username 用户名
     * @param rememberMe 记住我
     * @param password 密码
     * @param userCookie 用户cookie
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping(value = "/loginResult")
    public ResponseResult loginResult(@RequestParam(value = "username") String username,
                                               @RequestParam(value = "rememberMe", defaultValue = "1") Integer rememberMe,
                                               @RequestParam(value = "password") String password,
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
        int cacheTime = DigitConstant.DEFAULT_CACHE_TIME;
        if (rememberMe == 1) {
            cacheTime = DigitConstant.REMEMBER_ME_CACHE_TIME;
        }
        systemUserService.setUserInfo(userCookie, sysUser, cacheTime);

        return new ResponseResult(DigitConstant.SUCCESS_LOGED, sysUser, StringConsant.SUCCESS_LOGED);
    }

    /**
     * 注册逻辑
     *
     * @param username 用户名
     * @param password 密码
     * @param rePassword 重复密码
     * @param email 用户邮箱
     * @param userCookie 用户cookie
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping("/registerResult")
    public ResponseResult registerResult(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "password") String password,
                                         @RequestParam(value = "rePassword") String rePassword,
                                         @RequestParam(value = "email") String email,
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
        //新注册用户默认锁定
        Byte isLocked = 0;
        SystemUser systemUser = new SystemUser(null, username, MD5Password, createBy, email, isLocked);
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
     * @param username 用户名
     * @param email 邮箱
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/sendResetPasswordEmail")
    public ResponseResult sendResetPasswordEmail(@RequestParam(value = "username") String username,
                                                 @RequestParam(value = "email") String email) {
        //检测用户邮箱是否存在
        if (!systemUserService.checkRegisterSystemUser(username, email)) {
            return new ResponseResult(DigitConstant.ACCOUNT_NOEXISTS, "", StringConsant.ACCOUNT_NOEXISTS);
        }
        //验证用户名是否非法
        if (StringUtil.isSqlInjection(username)) {
            return new ResponseResult(DigitConstant.USERNAME_OR_PASSWORD_INJECTION, "", StringConsant.USERNAME_OR_PASSWORD_INJECTION);
        }
        //验证邮箱是否非法
        if (!StringUtil.isEmail(email) || StringUtil.isSqlInjection(email)) {
            return new ResponseResult(DigitConstant.EMAIL_ERROR, "", StringConsant.EMAIL_ERROR);
        }
        //检测用户信息是否正在
        if (!systemUserService.checkRegisterSystemUser(username, email)) {
            return new ResponseResult(DigitConstant.ACCOUNT_NOEXISTS, "", StringConsant.ACCOUNT_NOEXISTS);
        }
        //发送重置密码邮件
        String EncodeString = username + ":" + email;
        String setPasswordSalt = StringUtil.base64Encode(EncodeString.getBytes());
        String emailContent = StringConsant.RESET_PASSWORD_NOTE + host + "resetPassword/" + setPasswordSalt;
        mailService.sendSimpleMail(email, StringConsant.RESET_PASSWORD_SUBJECT, emailContent);

        return new ResponseResult(DigitConstant.SEND_EMAIL_SUCCESS, "", StringConsant.SEND_EMAIL_SUCCESS);
    }

    /**
     * 通过重置密码链接，展示用户修改密码界面
     *
     * @param base64String 加密串
     * @param map map
     * @return string
     */
    @RequestMapping("/resetPassword/{base64String}")
    public String resetPassword(@PathVariable(value = "base64String") String base64String, Map map) {
        byte[] base64User = StringUtil.base64Decode(base64String);
        String base64DecodeString = new String(base64User);
        String[] decodeUser = base64DecodeString.split(":");
        boolean resetFlag = true;
        //检测用户名参数
        if ("".equals(decodeUser[0]) || StringUtil.isSqlInjection(decodeUser[0])) {
            resetFlag = false;
            map.put("code", DigitConstant.USER_NAME_ERROR);
            map.put("message", StringConsant.USER_NAME_ERROR);
        }
        //检测邮箱参数
        if ("".equals(decodeUser[1]) || StringUtil.isSqlInjection(decodeUser[1])) {
            resetFlag = false;
            map.put("code", DigitConstant.EMAIL_ERROR);
            map.put("message", StringConsant.EMAIL_ERROR);
        }
        //检测账户信息
        if (!systemUserService.checkRegisterSystemUser(decodeUser[0], decodeUser[1])) {
            resetFlag = false;
            map.put("code", DigitConstant.ACCOUNT_NOEXISTS);
            map.put("message", StringConsant.ACCOUNT_NOEXISTS);

        }
        //如果请求参数有误，则跳转至重置结果页
        if (! resetFlag) {
            return "page/reset-forgot-result";
        }

        map.put("username", decodeUser[0]);
        map.put("email", decodeUser[1]);
        map.put("code", DigitConstant.RESET_PASSWORD_SUCCESS);
        map.put("message", StringConsant.RESET_PASSWORD_SUCCESS);

        return "page/reset-password";
    }

    /**
     * 重置密码
     *
     * @param password 密码
     * @param rePassword 重复密码
     * @param username 用户名
     * @param email 邮箱
     * @return ResponseResult
     */
    @ResponseBody
    @RequestMapping("/updateForgotPassword")
    public ResponseResult updateForgotPassword(@RequestParam(value = "password") String password,
                                       @RequestParam(value = "rePassword") String rePassword,
                                       @RequestParam(value = "username") String username,
                                       @RequestParam(value = "email") String email) {
        //检测用户信息
        if (StringUtil.isSqlInjection(username)) {
            return new ResponseResult(DigitConstant.USER_NAME_ERROR, "", StringConsant.USER_NAME_ERROR);
        }
        //检测邮箱参数
        if (StringUtil.isSqlInjection(email)) {
            return new ResponseResult(DigitConstant.EMAIL_ERROR, "", StringConsant.EMAIL_ERROR);
        }
        //检测账户信息
        if (! systemUserService.checkRegisterSystemUser(username, email)) {
            return new ResponseResult(DigitConstant.ACCOUNT_NOEXISTS, "", StringConsant.ACCOUNT_NOEXISTS);
        }
        //检测密码是否合法
        if (! password.equals(rePassword)) {
            return new ResponseResult(DigitConstant.REPASSWORD_DIFFERENT, "", StringConsant.REPASSWORD_DIFFERENT);
        }
        if (StringUtil.isSqlInjection(password) || StringUtil.isSqlInjection(rePassword)) {
            return new ResponseResult(DigitConstant.RESET_PASSWORD_ERROR, "", StringConsant.RESET_PASSWORD_ERROR);
        }
        if (username == password) {
            return new ResponseResult(DigitConstant.USERNAME_PASSWORD_SAME_ERROR, "", StringConsant.USERNAME_PASSWORD_SAME_ERROR);
        }
        //修改密码
        String encodePassword = StringUtil.getMD5String(password, StringConsant.PASSWORD_SALT);
        if (! systemUserService.updateForgotPassword(username, email, encodePassword)) {
            return new ResponseResult(DigitConstant.RESET_PASSWORD_FAIL, "", StringConsant.RESET_PASSWORD_FAIL);
        }

        return new ResponseResult(DigitConstant.RESET_PASSWORD_SUCCESS, "", StringConsant.RESET_PASSWORD_SUCCESS);
    }

    /**
     * 用户重置密码结果页
     *
     * @param userCookie 用户名cookie
     * @return String
     */
    @RequestMapping("/resetPasswordResult")
    public String resetPasswordResult(@CookieValue("user") String userCookie) {
        //如果用户已经登录，则跳转至用户中心
        if (systemUserService.checkUserIsLogged(userCookie)) {
            return "redirect:/admin";
        }

        return "page/reset-password-result";
    }

    /**
     * 用户登出
     *
     * @param userCookie 用户cookie
     * @return String
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue("user") String userCookie) {
        //清除用户redis信息
        systemUserService.logout(userCookie);

        return "redirect:/login";
    }

}
