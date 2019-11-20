package com.sunny.hospital.permission.init;

import com.sunny.hospital.permission.repository.PermissionRepository;
import com.sunny.hospital.permission.repository.RoleRepository;
import com.sunny.hospital.permission.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/26 17:15
 * @Description: TODO 初始化 配置从数据库中加载角色和权限
 * @Version 1.0
 */
@Service
public class DataInit {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionReporitory;



    /**
     * 此方法用于初始化账号信息
     * */
    @PostConstruct
    public void dataInit() {
//        System.out.println("【开始初始化角色信息】");
//        Role adminRole=new Role("admin","管理员");
//        Role normalRole=new Role("normal","普通用户");
//        roleRepository.save(adminRole);
//        roleRepository.save(normalRole);
//
//        //管理员和用户双角色
//        List<Role> roles=new ArrayList<>();
//        roles.add(adminRole);
//        roles.add(normalRole);
//        //管理员角色
//        List<Role> roles2 = new ArrayList<>();
//        roles2.add(adminRole);
//        //用户角色
//        List<Role> roles3 = new ArrayList<>();
//        roles3.add(normalRole);
//
//        System.out.println("【开始初始化用户信息】");
//        UserInfo admin = new UserInfo();
//        admin.setUsername("admin");
//        admin.setPassword(passwordEncoder.encode("123"));
//        admin.setRoles(roles);
//        userInfoRepository.save(admin);
//
//        UserInfo User = new UserInfo();
//        User.setUsername("User");
//        User.setPassword(passwordEncoder.encode("123"));
//        User.setRoles(roles3);
//        userInfoRepository.save(User);
//
//        System.out.println("【开始初始化动态权限信息】");
//        //基于URL的动态权限
//        //p.2 permission.save
//        //permission.
//        Permission permission1 = new Permission();
//        permission1.setUrl("/hello/helloUser");
//        permission1.setName("普通用户URL");
//        permission1.setDescription("普通用户的访问路径");
//        permission1.setRoles(roles);
//        permissionReporitory.save(permission1);
//
//        Permission permission2 = new Permission();
//        permission2.setUrl("/hello/helloAdmin");
//        permission2.setName("管理员URL");
//        permission2.setDescription("管理员的访问路径");
//        permission2.setRoles(roles2);
//        permissionReporitory.save(permission2);
//
//        Permission permission3=new Permission();
//        permission3.setUrl("/findAllqy");;
//        permission3.setName("全部都行");
//        permission3.setRoles(roles);
//        permissionReporitory.save(permission3);

    }
}
