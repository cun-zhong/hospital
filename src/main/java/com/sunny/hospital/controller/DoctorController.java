package com.sunny.hospital.controller;

import com.sunny.hospital.entity.Doctor;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.service.DoctorService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: TODO 医生控制器
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "doctor")
public class DoctorController {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalController.class);

    @Autowired
    private DoctorService doctorService;

    /**
     * 添加医生信息
     */
    @PostMapping("addDoctor")
    @ResponseBody
    public Result addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }


    /**
     * 修改医生信息
     */
    @PostMapping("updateDoctor")
    @ResponseBody
    public Result updateDoctor(@RequestBody Doctor doctor) {
        return doctorService.updateDoctor(doctor);
    }


    /**
     * 删除医生信息
     */
    @GetMapping("deleteById")
    @ResponseBody
    public Result deleteById(Integer id) {
        return doctorService.deleteById(id);
    }


    /**
     * 筛选医生
     */
    @PostMapping("queryDoctor")
    @ResponseBody
    public Result queryDoctor(@RequestBody JSONObject jsonObject){return doctorService.queryDoctor(jsonObject);}
}
