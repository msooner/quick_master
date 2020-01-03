package com.ron.mapper;

import com.ron.common.constants.StringConsant;
import com.ron.entity.SystemUser;
import com.ron.utils.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @auther Ron
 * @date 2019/10/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:application.yml")
@SpringBootTest
public class SystemUserMapperTest {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Test
    public void findAll() {
        List<SystemUser> all = systemUserMapper.getUsers();
        for (SystemUser user : all) {
            System.out.println(user.getId());
        }
    }

    @Test
    public void findById() {
        SystemUser user = systemUserMapper.getUser(1);
        System.out.println(user.getId());
    }

    @Test
    public void login() {
        String MD5Password = StringUtil.getMD5String("123456", StringConsant.PASSWORD_SALT);
        System.out.println(MD5Password);
        SystemUser row = systemUserMapper.getLoginUser("admin", MD5Password);
        System.out.println(row);
    }

    @Test
    public void testGetSystemUserList() {
        List<SystemUser> systemUsers = systemUserMapper.getSystemUserList();
        System.out.println(systemUsers);
    }
}