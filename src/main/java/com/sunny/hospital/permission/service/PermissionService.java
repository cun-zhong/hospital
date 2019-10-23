package com.sunny.hospital.permission.service;


import com.sunny.hospital.permission.bean.Permission;
import com.sunny.hospital.permission.bean.Role;
import com.sunny.hospital.permission.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/30 11:31
 * @Description: TODO 动态权限扩展
 * @Version 1.0
 */
@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionReporitory;

    /*
     * 这里map中的value是Collection<ConfigAttribute>的原因是
     * 在SecurityMetadataSource接口的getAttributes方法的返回值就是
     * Collection<ConfigAttribute>类型，这样方便在匹对正确的情况下直接返回。
     */
    private Map<String, Collection<ConfigAttribute>> permissionMap =null;

    /*
     * 初始化权限
     */
    @PostConstruct
    public void initPermissions() {
        System.out.println("PermissionServiceImpl.initPermissions()");
        permissionMap = new HashMap<>();
        Collection<ConfigAttribute> collection;
        ConfigAttribute cfg;

        List<Permission> permissions = permissionReporitory.findAll();
        for(Permission p:permissions) {
            collection = new ArrayList<ConfigAttribute>();
            for(Role r:p.getRoles()) {
                cfg = new SecurityConfig("ROLE_"+r.getName());
                collection.add(cfg);
            }
            //权限列表集合 URL 对应 角色-权限
            permissionMap.put(p.getUrl(),collection);
        }
        System.out.println(permissionMap);
    }


    public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        if(permissionMap.size()==0) initPermissions();
        return permissionMap;
    }
}
