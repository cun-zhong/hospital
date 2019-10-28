package com.sunny.hospital.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/21 16:21
 * @Description: 管理员实体类对象
 * @Version 1.0
 */
@Entity
@Table(name = "admin_user")
@DynamicInsert
@DynamicUpdate
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //管理员用户名
    private String adminName;
    //管理员密码
    private String adminPassword;
    //角色权限
    private String userRole;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdTime;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date updatedTime;

}
