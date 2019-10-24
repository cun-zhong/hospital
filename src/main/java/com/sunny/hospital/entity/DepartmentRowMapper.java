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

public class DepartmentRowMapper implements RowMapper, Serializable {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("id"));
        department.setHisDepartmentId(resultSet.getString("his_department_id"));
        department.setHisDepartmentName(resultSet.getString("his_department_name"));
        department.setHospitalCode(resultSet.getNString("hospital_code"));
        department.setIntroduction(resultSet.getString("introduction"));
        department.setCreatedTime(resultSet.getDate("created_time"));
        department.setUpdatedTime(resultSet.getDate("updated_time"));
        return department;
    }
}
