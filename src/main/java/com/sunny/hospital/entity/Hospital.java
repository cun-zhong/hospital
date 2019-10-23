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
 * @Description: 医院实体类对象
 * @Version 1.0
 */
@Entity
@Table(name = "hospital")
@DynamicInsert
@DynamicUpdate
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class Hospital {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  //医院编码
  private String hospitalCode;
  //医院名称
  private String hospitalName;
  //头像地址
  private String hospitalHeadImage;
  //医院照片地址
  private String hospitalImage;
  //医院地址
  private String address;
  //手机号
  private String tel;
  //医院简介
  private String hospitalInfo;
  //创建时间
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  private Date createTime;
  //创建时间
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  private Date updateTime;
  //医院等级
  private String level;
  //评论输了
  private Integer commentNum;
  //订单数量
  private Integer payNum;

}
