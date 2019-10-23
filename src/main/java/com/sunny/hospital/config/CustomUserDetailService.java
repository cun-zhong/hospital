package com.sunny.hospital.config;


import com.sunny.hospital.permission.bean.Role;

import com.sunny.hospital.permission.bean.UserInfo;
import com.sunny.hospital.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/26 17:01
 * @Description: TODO 配置从数据库中加载权限和角色
 * @Version 1.0
 */
@SuppressWarnings("ALL")
@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserInfoService userInfoService;
    /**
     * （1）    通过username获取用户的信息。
     *
     * （2）    定义一个User(实现了接口UserDetails)对象，返回用户的username,passowrd和权限列表。
     *
     * （3）    需要注意，定义角色集的时候，需要添加前缀“ROLE_”。
     *
     * （4）    这里的密码需要使用PasswordEncoder进行加密，否则会报“无效的凭证”。
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailService.loadUserByUsername()");
        //通过username获取用户信息
        UserInfo userInfo = userInfoService.findByUsername(username);
        if(userInfo == null) {
            throw new UsernameNotFoundException("not found");
        }


        //定义权限列表.
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        for (Role role:userInfo.getRoles()){
            authorities.add(new SimpleGrantedAuthority(("ROLE_"+role.getName())));
        }

        User userDetails = new User(userInfo.getUsername(),userInfo.getPassword(),authorities);
        return userDetails;
    }
}
