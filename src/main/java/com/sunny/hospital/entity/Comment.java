package com.sunny.hospital.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/21 16:21
 * @Description: 评论实体类对象
 * @Version 1.0
 */
@Entity
@Table(name = "comment")
@DynamicInsert
@DynamicUpdate
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  //订单id
  private Integer orderId;
  //父id 如果不设置 默认为0
  private Integer pId=0;
  //被回复者用户名
  private String replyUserName;
  //被回复者id
  private Integer replyUserId;
  //评论人id
  private Integer userId;
  //评论人用户名
  private String userName;
  //医生头像
  private String userImg;
  //内容
  private String content;
  //分数
  private Integer score;
  //创建时间
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  private Date createdTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orderId",insertable = false,updatable = false)
  private BookingOrder bookingOrder;//所属文章

  //二级评论回复 采用一对多的映射关系
  @OneToMany(fetch = FetchType.LAZY)//不设置级联，使用懒加载
  @JoinColumn(name = "pId", referencedColumnName = "id")
  @OrderBy("id ASC")
  private List<Comment> commentList;



}
