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
 * @Description: 科室实体类对象
 * @Version 1.0
 */
@Entity
@Table(name = "department")
@DynamicInsert
@DynamicUpdate
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //医院编码
    private String hospitalCode;
    //医院名称
    private String hospitalName;
    //医院科室名称
    private String hisDepartmentName;
    //医院科室介绍
    private String introduction;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdTime;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date updatedTime;

}
