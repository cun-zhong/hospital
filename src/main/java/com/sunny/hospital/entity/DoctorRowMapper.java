package com.sunny.hospital.entity;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/21
 * @Description: 封装医生返回对象 将数据库对象转化为实体类对象
 * @Version 1.0
 */
public class DoctorRowMapper implements RowMapper, Serializable {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getInt("id"));
        doctor.setHospitalCode(resultSet.getString("hospital_code"));
        doctor.setHisDepartmentId(resultSet.getString("his_department_id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setHisDepartmentName(resultSet.getString("his_department_name"));
        doctor.setFeat(resultSet.getString("feat"));
        doctor.setIntroduce(resultSet.getString("introduce"));
        doctor.setTitle(resultSet.getString("title"));
        doctor.setTel(resultSet.getString("tel"));
        doctor.setHeadUrl(resultSet.getString("head_url"));
        doctor.setHospitalName(resultSet.getString("hospital_name"));
        doctor.setTime(resultSet.getString("time"));
        doctor.setGender(resultSet.getInt("gender"));
        doctor.setBookingNum(resultSet.getInt("booking_num"));
        doctor.setHourPeople(resultSet.getInt("hour_people"));
        doctor.setCreatedTime(resultSet.getDate("created_time"));
        doctor.setUpdatedTime(resultSet.getDate("updated_time"));

        return doctor;
    }
}
