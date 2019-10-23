package com.sunny.hospital.config;


import com.sunny.hospital.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/30 17:35
 * @Description: TODO 自定义加密算法
 * @Version 1.0
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Util.encode((String)rawPassword));
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encode((String)rawPassword);
    }
}