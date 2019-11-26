package com.ron.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendSimpleMail() throws Exception {

        mailService.sendSimpleMail("conan@jollycorp.com", "This is a test!", "你猜我想干嘛?");
    }

    @Test
    public void sendHtmlMail() throws Exception {
    }

    @Test
    public void sendAttachmentsMail() throws Exception {
    }

}