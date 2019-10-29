package com.sunny.hospital.controller;

import com.sunny.hospital.entity.Department;
import com.sunny.hospital.entity.Doctor;
import com.sunny.hospital.entity.Hospital;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.service.DepartmentService;
import com.sunny.hospital.service.DoctorService;
import com.sunny.hospital.service.HospitalService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;


    /**
     * 查询医生信息
     * */
    @GetMapping("findDoctorById")
    @ResponseBody
    public Doctor findDoctorById(Integer id){
        Doctor byId = doctorService.findById(id);
        return byId;
    }

    /**
     * @deprecated 添加或修改医生信息页面
     * */
    @GetMapping("updateOrAddDoctor")
    public String updateOrAddDoctor(Integer id, ModelMap modelMap){
        List<Hospital> all = hospitalService.findAll();
        modelMap.addAttribute("hospitals",all);
        if (id!=null){
            Doctor byId = doctorService.findById(id);
            modelMap.addAttribute("doctor",byId);
        }else {
            modelMap.addAttribute("doctor",new Doctor());
        }
        return "doctor/updateOrAddDoctor";
    }

    /**
     * 管理医生页面
     * */
    @GetMapping("doctorManagePage")
    public String doctorManagePage(Model model){
        List<Hospital> all = hospitalService.findAll();
        model.addAttribute("hospitals",all);
        return "doctor/doctorManage";
    }

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
