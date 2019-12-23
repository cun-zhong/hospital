package com.sunny.hospital.dao;

import com.sunny.hospital.entity.BookingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/23
 * @Description:
 * @Version 1.0
 */
@Transactional
public interface BookingOrderDao extends JpaRepository<BookingOrder,Long> {
    //根据订单id进行查询
    BookingOrder findById(Integer id);

    //查询所有待就诊信息
    List<BookingOrder> findAllByDoctorIdAndStatus(Integer doctorId,Integer status);

    //查询医生该时间段内所有挂号信息
    List<BookingOrder> findAllByDoctorIdAndChooseDateAndTimeRange(Integer doctorId, Date choDate,String timeRange);

    //该医生该时间段待就诊信息
    List<BookingOrder> findAllByDoctorIdAndChooseDateAndTimeRangeAndStatus(
            Integer doctorId, Date choDate,String timeRange,Integer status);

    //查询用户当前半天的挂号信息
    List<BookingOrder> findByDoctorIdAndAmAndChooseDateAndUserIdAndStatus(
            Integer doctorId,String am,Date cDate,Integer userId,Integer status);

    //根据状态查询挂号订单
    List<BookingOrder> findAllByStatus(Integer status);

    //根据医生和状态查询挂号订单
    List<BookingOrder> findAllByStatusAndDoctorId(Integer status,Integer doctorId);


}
