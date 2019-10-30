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
        bookingOrder.setHisDepartmentId(resultSet.getString("his_department_id"));
        bookingOrder.setHisDepartmentName(resultSet.getString("his_department_name"));
        bookingOrder.setHisDoctorId(resultSet.getString("his_doctor_id"));
        bookingOrder.setHisDoctorName(resultSet.getString("his_doctor_name"));

        bookingOrder.setHisDoctorTitle(resultSet.getString("his_doctor_title"));
        bookingOrder.setSourceId(resultSet.getString("source_id"));
        bookingOrder.setSourceNum(resultSet.getString("source_num"));
        bookingOrder.setReservationTime(resultSet.getString("reservation_time"));
        bookingOrder.setPcode(resultSet.getString("pcode"));

        bookingOrder.setWorkId(resultSet.getString("work_id"));
        bookingOrder.setUserId(resultSet.getInt("user_id"));
        bookingOrder.setStatus(resultSet.getInt("status"));

        bookingOrder.setForDateDay(resultSet.getDate("for_date_day"));
        bookingOrder.setCreatedTime(resultSet.getDate("created_time"));
        bookingOrder.setUpdatedTime(resultSet.getDate("updated_time"));

        return bookingOrder;

    }
}
