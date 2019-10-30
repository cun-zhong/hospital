package com.sunny.hospital.dao;

import com.sunny.hospital.entity.BookingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

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

    //

}
