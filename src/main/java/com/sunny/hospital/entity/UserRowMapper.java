package com.sunny.hospital.entity;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/21
 * @Description: 普通用户返回对象 将数据库对象转化为实体类对象
 * @Version 1.0
 */
public class UserRowMapper implements RowMapper, Serializable {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setIntegral(resultSet.getInt("integral"));
        user.setGender(resultSet.getInt("gender"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        user.setIdCard(resultSet.getString("id_card"));
        user.setTel(resultSet.getString("tel"));
        user.setCreatedTime(resultSet.getDate("created_time"));
        user.setUpdatedTime(resultSet.getDate("updated_time"));

        return user;
    }

}
