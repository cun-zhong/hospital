package com.sunny.hospital.permission.bean;


import javax.persistence.*;
import java.util.List;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/26 16:56
 * @Description: TODO 基于数据库的身份认证和角色授权
 * @Version 1.0
 */
@Entity
public class UserInfo {


    @Id @GeneratedValue
    private long uid;//主键.
    private String username;//用户名.
    private String password;//密码.

    //用户－－角色：多对多的关系．
    @ManyToMany(fetch=FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "UserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "role_id") })
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}