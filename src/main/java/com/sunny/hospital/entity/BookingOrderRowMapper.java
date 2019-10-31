package com.sunny.hospital.entity;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/21
 * @Description: 封装预约挂号信息管理订单返回对象 将数据库对象转化为实体类对象
 * @Version 1.0
 */
public class BookingOrderRowMapper implements RowMapper, Serializable {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        BookingOrder bookingOrder = new BookingOrder();
        bookingOrder.setId(resultSet.getInt("id"));
        bookingOrder.setHospitalCode(resultSet.getString("hospital_code"));
        bookingOrder.setHospitalName(resultSet.getString("hospital_name"));
        bookingOrder.setHisDepartmentId(resultSet.getInt("his_department_id"));
        bookingOrder.setHisDepartmentName(resultSet.getString("his_department_name"));
        bookingOrder.setDoctorId(resultSet.getInt("doctor_id"));
        bookingOrder.setDoctorName(resultSet.getString("doctor_name"));
        bookingOrder.setDoctorTitle(resultSet.getString("doctor_title"));
        bookingOrder.setChooseDate(resultSet.getDate("choose_date"));
        bookingOrder.setRegisterTime(resultSet.getDate("register_timer"));
        bookingOrder.setAm(resultSet.getString("am"));
        bookingOrder.setTimeRange(resultSet.getString("time_range"));
        bookingOrder.setFinishTime(resultSet.getDate("finish_time"));
        bookingOrder.setUserId(resultSet.getInt("user_id"));
        bookingOrder.setStatus(resultSet.getInt("status"));
        bookingOrder.setCreatedTime(resultSet.getDate("created_time"));
        bookingOrder.setUpdatedTime(resultSet.getDate("updated_time"));
        bookingOrder.setSort(resultSet.getInt("sort"));
        bookingOrder.setRangeSort(resultSet.getString("range_sort"));

        return bookingOrder;

    }
}
