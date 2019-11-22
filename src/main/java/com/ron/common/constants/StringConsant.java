package com.ron.common.constants;

/**
 * 字符串常量类
 */
public final class StringConsant {

    //登录相关
    public static final String USER_NAME_NOT_EXIST = "用户名或密码错误!";
    public static final String USER_COOKIE_ERROR = "登录信息错误!";
    public static final String USER_NAME_ERROR = "用户名为空或错误!";
    public static final String PASSWORD_ERROR = "密码为空或错误!";
    public static final String USERNAME_OR_PASSWORD_ERROR = "密码或密码错误!";
    public static final String ALREADY_LOGGED_ERROR = "请不要重复登录";
    public static final String SUCCESS_LOGED = "您已成功登录!";

    //注册相关
    public static final String REPASSWORD_DIFFERENT = "两次输入的密码不同!";
    public static final String USERNAME_ALREADLY_EXISTS = "用户名或邮箱已经存在!";
    public static final String REGISTER_FAIL = "用户注册失败!";
    public static final String EMAIL_ERROR = "邮箱地址错误!";
    public static final String USERNAME_OR_PASSWORD_INJECTION = "用户名或密码含有非法字符!";
    public static final String USERNAME_PASSWORD_SAME_ERROR = "用户名和密码不能同相!";

    //找回密码相关
    public static final String ACCOUNT_NOEXISTS = "您的账户信息不存在!";
    public static final String SEND_EMAIL_SUCCESS = "重置密码的链接邮件已成功发送!";

    //加密salt
    public static final String PASSWORD_SALT = "hao123";

}
