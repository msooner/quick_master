package com.ron.service.impl;

import com.ron.entity.SystemUser;
import com.ron.service.SystemUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @auther Ron
 * @date 2019/10/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SeckillServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemUserService systemUserService;

    @Test
    public void findAll() {
        SystemUser systemUser = systemUserService.getUserById(1);
        logger.info("all={}", systemUser);
    }

    @Test
    public void findByIdFormRedis() {

    }

}