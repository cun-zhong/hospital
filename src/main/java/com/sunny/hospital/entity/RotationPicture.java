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
@Table(name = "rotation_picture")
@DynamicInsert
@DynamicUpdate
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class RotationPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //医院名称
    private String hospitalName;

    //所属医院
    private String  pictureName;

    //图片地址
    private String pictureUrl;
}
