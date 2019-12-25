package com.sunny.hospital.service;

import com.sunny.hospital.dao.BookingOrderDao;
import com.sunny.hospital.dao.CommentDao;
import com.sunny.hospital.entity.BookingOrder;
import com.sunny.hospital.entity.Comment;
import com.sunny.hospital.entity.Doctor;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.permission.bean.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: 孙宇豪
 * @Date: 2019/12/24 9:42
 * @Description: TODO 评论业务层
 * @Version 1.0
 */
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BookingOrderDao bookingOrderDao;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DoctorService doctorService;

    private static final Logger logger= LoggerFactory.getLogger(CommentService.class);

    /**
     * 根据订单id查询评论
     * */
    public Comment getCommentByOrderId(Integer orderId){
        return commentDao.findByOrderId(orderId);
    }

    /**
     * @deprecated 删除评论
     */
    public Result deleteById(Integer id){
        try {
            this.commentDao.deleteById(id);
            return new Result(0,"删除成功");
        }catch (Exception e){
            logger.error("删除失败"+e);
            return new Result(-1,"删除失败");
        }
    }

    /**
     * @deprecated 查询评论条数
     * */
    public Result countCommentSizeByOrderId(Integer orderId){
        try {
            int size = commentDao.countByOrderId(orderId);
            return new Result(size);
        }catch (Exception e){
            logger.error(e.toString());
            return new Result(-1,"获取失败");
        }
    }

    /**
     * @deprecated 查询单个评论
     * */
    public Result getCommentById(Integer id){
        try {
            Comment byId = commentDao.findById(id);
            return new Result(byId);
        }catch (Exception e){
            logger.error(e.toString());
            return new Result(-1,"查询失败");
        }
    }

    /**
     * @deprecated 新增评论
     * */
    public Result addComment(Comment comment){
        try {
            //评论时间
            comment.setCreatedTime(new Date());
            //获取当前登陆用户
            User users = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserInfo user = userInfoService.findByUsername(users.getUsername());
            //获取用户唯一标识
            long uid = user.getUid();
            comment.setUserId((int)uid);
            comment.setUserName(user.getUsername());
            commentDao.save(comment);
            //获取订单对象
            BookingOrder byId = bookingOrderDao.findById(comment.getOrderId());
            //修改订单评论状态
            byId.setCommentType(1);
            bookingOrderDao.save(byId);
            return new Result(0,"评论成功");
        }catch (Exception e){
            logger.error(e.toString());
            return new Result(-1,"评论失败");
        }
    }

    /**
     * @deprecated 回复
     */
    public Result replyComment(Comment comment){
        try {
            //获取当前登陆用户
            User users = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserInfo user = userInfoService.findByUsername(users.getUsername());
            if (user.getRoles().get(0).getName().equals("doctor")){
                Doctor byUsername = doctorService.findByUsername(users.getUsername());
                comment.setUserImg(byUsername.getHeadUrl());
            }
            int uid = (int) user.getUid();
            comment.setCreatedTime(new Date());
            comment.setUserId(uid);
            comment.setUserName(user.getUsername());
            comment.setPId(comment.getId());
            comment.setId(null);
            //如果回复id不为空
            if(comment.getReplyUserId()!=null){
                UserInfo userById = userInfoService.findUserById(comment.getReplyUserId());
                comment.setReplyUserName(userById.getUsername());
            }
            commentDao.save(comment);
            return new Result(0,"回复成功");
        }catch (Exception e){
            logger.error(e.toString());
            return new Result(-1,"回复失败");
        }
    }
}
