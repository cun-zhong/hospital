package com.sunny.hospital.controller;


import com.sunny.hospital.entity.BookingOrder;
import com.sunny.hospital.entity.Comment;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.service.BookingOrderService;
import com.sunny.hospital.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 孙宇豪
 * @Date: 2019/12/24 10:20
 * @Description: TODO 评论控制器
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BookingOrderService bookingOrderService;

    /**
     * 评论页 订单详情页
     * */
    @GetMapping("orderDetail")
    public String orderDetail(Integer id, Model model){
        if(id==null){
            id=1;
        }
        BookingOrder byId = bookingOrderService.findById(id);
        model.addAttribute("order",byId);
        Comment comment = byId.getComment();
        if (comment==null){
            comment=new Comment();
        }
        model.addAttribute("comment",comment);
        return "booking/msgboard";
    }

    /**
     * @deprecated 发表评论
     * */
    @PostMapping("addComment")
    @ResponseBody
    public Result addComment(@RequestBody Comment comment){
        //如果没有commentid
        Result result;
        if (comment.getId()==null){
                result=commentService.addComment(comment);
        }else {
            result=commentService.replyComment(comment);
        }
        return result;
    }
}
