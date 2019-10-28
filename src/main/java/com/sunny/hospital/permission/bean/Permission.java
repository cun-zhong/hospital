package com.sunny.hospital.permission.bean;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/30 11:14
 * @Description: TODO
 * @Version 1.0
 */
@Entity
public class Permission {
    @Id
    @GeneratedValue
    private long id;//主键.

    private String name;//权限名称.

    private String description;//权限描述.

    /**
     *  注意：Permission 表的url通配符为两颗星，比如说 /user下的所有url，应该写成 /User/**;
     */
    private String url;//授权链接

    private long pid;//父节点id.


    // 角色 - 权限是多对多的关系
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="RolePermission",joinColumns= {@JoinColumn(name="permission_id")} , inverseJoinColumns= {@JoinColumn(name="role_id")})
    private List<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}