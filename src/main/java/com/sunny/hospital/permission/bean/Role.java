package com.sunny.hospital.permission.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/29 17:53
 * @Description: TODO 动态加载角色 角色对象
 * @Version 1.0
 */
@Entity
public class Role {
    @Id
    @GeneratedValue
    private long rid;//主键.
    private String name;//角色名称.
    private String description;//角色描述.

    public Role() {
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getRid() {
        return rid;
    }
    public void setRid(long rid) {
        this.rid = rid;
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

}