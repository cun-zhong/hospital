package com.sunny.hospital.controller;

import com.sunny.hospital.entity.Result;
import com.sunny.hospital.entity.RotationPicture;
import com.sunny.hospital.service.RotationPictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
     * 轮播管理页面
     * */
    @GetMapping("rotationPictureManage")
    public String rotationPicture(){
        return "hospital/rotationPicture";
    }

    /**
     * 编辑轮播图页面
     * */
    @GetMapping("updateOrAddPage")
    public String updateOrAddPage(Integer id, ModelMap modelMap){
        if (id!=null){
            RotationPicture byId = rotationPictureService.findById(id);
            modelMap.addAttribute("image",byId);
        }else {
            modelMap.addAttribute("image",new RotationPicture());
        }
        return "hospital/updateOrAddRPicture";
    }

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
    @GetMapping("findAll")
    @ResponseBody
    public  Result findAll(){
        return rotationPictureService.findAll();
    }
}
