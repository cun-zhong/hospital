package com.sunny.hospital.controller;

import com.sunny.hospital.entity.Hospital;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.service.HospitalService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: TODO 医院控制器
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "hospital")
public class HospitalController {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalController.class);

    @Autowired
    private HospitalService hospitalService;

    /**
     * @deprecated 首页
     * */
    @GetMapping("/index")
    public String blogIndex(){
        return "hospital/index";
    }

    /**
     * 医院展示页
     * */
    @GetMapping("hospitalShow")
    public String HospitalShow(){
        return "hospital/hospitalShow";
    }
    /**
     * 医院管理页面
     * */
    @GetMapping("/hospitalManagePage")
    public String hospitalManagePage(){
        return "hospital/hospitalManage";
    }

    /**
     * @deprecated 筛选查询医院接口
     * @param jsonObject 筛选条件对象
     * */
    @PostMapping("queryHospital")
    @ResponseBody
    public Result queryHospital(@RequestBody JSONObject jsonObject){
        return hospitalService.queryHospital(jsonObject);
    }

    @PostMapping("addHospital")
    public Result addHospital(@RequestBody Hospital hospital){

    }

}
