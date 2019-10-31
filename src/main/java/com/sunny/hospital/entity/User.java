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
 * @Description: 就诊人实体类对象
 * @Version 1.0
 */
@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户名
    private String username;
    //姓名
    private String name;
    //身份证号
    private String idCard;
    //性别  1男2女
    private Integer gender;
    //手机号
    private String tel;
    //密码
    private String password;
    //积分
    private Integer integral;

    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdTime;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date updatedTime;
}
