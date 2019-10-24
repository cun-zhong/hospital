package com.sunny.hospital.controller;

import com.sunny.hospital.entity.Result;
import com.sunny.hospital.entity.RotationPicture;
import com.sunny.hospital.service.RotationPictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: TODO 科室控制器
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "rotationPicture")
public class RotationPictureController {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(RotationPictureController.class);

    @Autowired
    private RotationPictureService rotationPictureService;


    /**
     * 修改轮播图片信息
     */
    @PostMapping("updateRotationPicture")
    @ResponseBody
    public Result updateRotationPicture(@RequestBody RotationPicture rotationPicture) {
        return rotationPictureService.updateRotationPicture(rotationPicture);
    }


    /**
     * 查看所有轮播图片信息
     */
    @PostMapping("findAll")
    @ResponseBody
    public  Result findAll(){
        return rotationPictureService.findAll();
    }
}
