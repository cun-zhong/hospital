package com.sunny.hospital.entity;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/21
 * @Description: 封装医院返回对象 将数据库对象转化为实体类对象
 * @Version 1.0
 */
public class HospitalRowMapper implements RowMapper, Serializable {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Hospital hospital=new Hospital();
        hospital.setId(resultSet.getInt("id"));
        hospital.setAddress(resultSet.getString("address"));
        hospital.setCommentNum(resultSet.getInt("comment_num"));
        hospital.setCreateTime(resultSet.getDate("create_time"));
        hospital.setHospitalCode(resultSet.getString("hospital_code"));
        hospital.setHospitalHeadImage(resultSet.getString("hospital_head_image"));
        hospital.setHospitalImage(resultSet.getString("hospital_image"));
        hospital.setHospitalInfo(resultSet.getString("hospital_info"));
        hospital.setHospitalName(resultSet.getString("hospital_name"));
        hospital.setLevel(resultSet.getString("level"));
        hospital.setPayNum(resultSet.getInt("pay_num"));
        hospital.setTel(resultSet.getString("tel"));
        hospital.setUpdateTime(resultSet.getDate("update_time"));
        return hospital;
    }
}
