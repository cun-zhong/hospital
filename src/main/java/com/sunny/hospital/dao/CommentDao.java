package com.sunny.hospital.dao;

import com.sunny.hospital.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 孙宇豪
 * @Date: 2019/12/24 9:34
 * @Description: TODO 评论dao层
 * @Version 1.0
 */
public interface CommentDao extends JpaRepository<Comment,Long> {

    //根据订单id查询评论数
    int countByOrderId(Integer orderId);

    //删除评论
    void deleteById(Integer id);

    //根据id查询评论
    Comment findById(Integer id);

    //根据订单号查询评论
    Comment findByOrderId(Integer orderId);
}
