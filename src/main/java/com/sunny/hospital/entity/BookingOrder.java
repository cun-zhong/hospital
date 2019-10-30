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
 * @Description: 挂号订单实体类对象
 * @Version 1.0
 */
@Entity
@Table(name = "booking_order")
@DynamicInsert
@DynamicUpdate
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class BookingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //医院名称
    private String hospitalName;
    //医院编码
    private String hospitalCode;
    //医院科室id
    private String hisDepartmentId;
    //医院科室名称
    private String hisDepartmentName;
    //医生id
    private String hisDoctorId;
    //医生名称
    private String hisDoctorName;
    //医生职称
    private String hisDoctorTitle;
    //号源id唯一标识
    private String sourceId;
    //挂号id唯一标识
    private String sourceNum;

    //预约时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date forDateDay;
    //预约时间段
    private String reservationTime;
    //预约时间段午别
    private String pcode;
    //排班标识
    private String workId;
    //用户标识
    private Integer userId;
    //状态
    private Integer status;

    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdTime;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date updatedTime;
}
