package com.sunny.hospital.controller;

import com.sunny.hospital.entity.AdminUser;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.service.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: TODO 管理员控制器
 * @Version 1.0
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "adminUser")
public class AdminUserController {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 添加管理员信息
     */
    @PostMapping("addAdminUser")
    @ResponseBody
    public Result addAdminUser(@RequestBody AdminUser adminUser){
        return adminUserService.addAdminUser(adminUser);
    }



    /**
     * 修改管理员信息
     */
    @PostMapping("updateAdminUser")
    @ResponseBody
    public Result updateAdminUser(@RequestBody AdminUser adminUser){
        return adminUserService.updateAdminUser(adminUser);
    }



    /**
     * 删除管理员信息
     */
    @GetMapping("deleteById")
    @ResponseBody
    public Result deleteById(Integer id){
        return adminUserService.deleteById(id);
    }
}
