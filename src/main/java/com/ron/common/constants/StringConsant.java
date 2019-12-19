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
    public static final String USER_IS_LOCKED = "您的账户被锁定，请联系管理员修复!";

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
    public static final String SET_PASSWORD_SALT = "get-password";
    public static final String RESET_PASSWORD_NOTE = "此邮件为重置密码邮件，请复制链接到浏览器的地址栏，回车后将进入重置密码界面: ";
    public static final String RESET_PASSWORD_SUBJECT = "重置密码!";
    public static final String RESET_PASSWORD_ERROR = "重置密码非法!";
    public static final String RESET_PASSWORD_SUCCESS = "重置密码成功!";
    public static final String RESET_PASSWORD_FAIL = "重置密码失败!";

    //管理员相关提示
    public static final String ADD_ADMIN_SUCCESS = "管理员信息已成功添加!";
    public static final String ADD_ADMIN_INFO_ERROR = "添加的管理员信息有误!";

    public static final String  DELETE_ADMIN_INFO_SUCCESS = "成功删除管理员信息!";
    public static final String  DELETE_ADMIN_INFO_FAIL = "删除管理员信息失败!";
    public static final String  DELETE_CURRENT_USER_ERROR = "不能删除当前用户!";

    //部门相关
    public static final String DELETE_DEPARTMENT_SUCCESS = "成功删除部门信息!";
    public static final String DELETE_DEPARTMENT_FAIL = "删除部门信息失败!";
    public static final String ADD_DEPARTMENT_SUCCESS = "成功添加部门信息!";
    public static final String ADD_DEPARTMENT_FAIL = "服务端错误,添加部门信息失败!";
    public static final String EDIT_DEPARTMENT_SUCCESS = "成功编辑部门信息!";
    public static final String EDIT_DEPARTMENT_FAIL = "服务端错误, 编辑部门信息失败!";
    public static final String EDIT_DEPARTMENT_INFO_ERROR = "部门编辑信息有误";
    public static final String ADD_DEPARTMENT_INFO_ERROR = "部门信息有误!";
    public static final String DEPARTMENT_INFO_ISEXISTS = "部门信息已经存在!";
    public static final String DEPARTMENT_INFO_NOT_EXISTS = "部门信息不存在!";

    //角色相关
    public static final String ADD_ROLE_SUCCESS = "成功添加角色信息!";
    public static final String ADD_ROLE_INFO_ERROR = "角色信息有误!";
    public static final String ROLE_INFO_IS_EXISTS = "角色信息已经存在!";
    public static final String ADD_ROLE_FAIL = "服务端错误,添加角色信息失败!";
    public static final String GET_ROLE_LIST_SUCCESS = "成功获取角色列表信息!";
    public static final String DELETE_ROLE_SUCCESS = "成功删除角色信息!";
    public static final String DELETE_ROLE_FAIL = "删除角色信息失败!";

    //模块相关
    public static final String ADD_MODULE_SUCCESS = "成功添加模块信息!";
    public static final String ADD_MODULE_INFO_ERROR = "模块信息有误!";
    public static final String MODULE_INFO_IS_EXISTS = "模块信息已经存在!";
    public static final String MODULE_INFO_NOT_EXISTS = "模块信息不存在!";
    public static final String ADD_MODULE_FAIL = "服务端错误,添加模块信息失败!";
    public static final String GET_MODULE_LIST_SUCCESS = "成功获取模块列表信息!";
    public static final String DELETE_MODULE_SUCCESS = "成功删除模块信息!";
    public static final String DELETE_MODULE_FAIL = "删除模块信息失败!";

    //参数相关
    public static final String REQUEST_PARAMETER_ERROR = "请求参数有误!";

    //加密salt
    public static final String PASSWORD_SALT = "hao123";

}
