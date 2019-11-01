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
    private Integer hisDepartmentId;
    //医院科室名称
    private String hisDepartmentName;
    //医生id
    private Integer doctorId;
    //医生名称
    private String doctorName;
    //医生职称
    private String doctorTitle;;

    //预约时间 选择的就诊日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date chooseDate;
    //挂号的时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)    private Date registerTime;
    //预约时间段午别
    private String am;
    //时间段 1-8代表8点-12点或者2点-6点，每一个小时代表一个时间段
    private String timeRange;
    //就诊结束时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date finishTime;
    //用户标识
    private Integer userId;
    //用户姓名
    private String patientName;
    //状态
    private Integer status;
    //预约序号
    private Integer sort;
    //时间段—序号
    private String rangeSort;

    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdTime;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Transient
    private String date;
}
