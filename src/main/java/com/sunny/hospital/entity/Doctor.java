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
 * @Description: 医生实体类对象
 * @Version 1.0
 */
@Entity
@Table(name = "doctor")
@DynamicInsert
@DynamicUpdate
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //医院名称
    private String hospitalName;
    //医院编码
    private String hospitalCode;
    //his的科室编号
    private String hisDepartmentId;
    //医生名字
    private String name;
    //his的科室名称
    private String hisDepartmentName;
    //医生擅长
    private String feat;
    //医生介绍
    private String introduce;
    //医生职称
    private String title;
    //医生联系方式
    private String tel;
    //性别  1男2女
    private Integer gender;
    //挂号量
    private Integer bookingNum;
    //头像地址
    private String headUrl;
    //每小时挂号的人数限制
    private Integer hourPeople;
    //门诊时间。1~10分别对应周一上午到周五下午。多个时间段以逗号连接
    private String time;

    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdTime;

    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date updatedTime;
}
