package com.ron.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;

@Configuration
public class EmailConfig {
    @Value("${spring.mail.host}")
    private static String host;
    @Value("${spring.mail.username}")
    private static String username;
    @Value("${spring.mail.password}")
    private static String password;
    @Value("${spring.mail.protocol}")
    private static String protocol;
    @Value("${spring.mail.port}")
    private static int port;
    @Value("${spring.mail.encoding}")
    private static String encoding;
    @Value("${spring.mail.timeout}")
    private static String timeout;
    @Value("${spring.mail.from}")
    private static String from;
    @Value("${spring.mail.personal}")
    private static String personal;

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        EmailConfig.host = host;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        EmailConfig.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        EmailConfig.password = password;
    }

    public static String getProtocol() {
        return protocol;
    }

    public static void setProtocol(String protocol) {
        EmailConfig.protocol = protocol;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        EmailConfig.port = port;
    }

    public static String getEncoding() {
        return encoding;
    }

    public static void setEncoding(String encoding) {
        EmailConfig.encoding = encoding;
    }

    public static String getTimeout() {
        return timeout;
    }

    public static void setTimeout(String timeout) {
        EmailConfig.timeout = timeout;
    }

    public static String getFrom() {
        return from;
    }

    public static void setFrom(String from) {
        EmailConfig.from = from;
    }

    public static String getPersonal() {
        return personal;
    }

    public static void setPersonal(String personal) {
        EmailConfig.personal = personal;
    }

    @Override
    public String toString() {
        return "EmailConfig{" +
                "host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", protocol='" + protocol + '\'' +
                ", port=" + port +
                ", encoding='" + encoding + '\'' +
                ", timeout='" + timeout + '\'' +
                ", from='" + from + '\'' +
                ", personal='" + personal + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(EmailConfig.getHost());
    }
}
