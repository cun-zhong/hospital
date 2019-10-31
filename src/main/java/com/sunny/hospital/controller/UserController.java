package com.sunny.hospital.controller;

import com.sunny.hospital.entity.Department;
import com.sunny.hospital.entity.Doctor;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.entity.User;
import com.sunny.hospital.service.UserService;
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
 * @Description: TODO 普通用户控制器
 * @Version 1.0
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "user")
public class UserController {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private UserService userService;

    /**
     * @deprecated 添加或修改用户信息页面
     * */
    @GetMapping("updateOrAddUser")
    public String updateOrAddUser(Integer id, ModelMap modelMap){
        if (id!=null){
            User userById = userService.findUserById(id);
            modelMap.addAttribute("user",userById);
        }else {
            modelMap.addAttribute("user",new User());
        }
        return "user/updateOrAddUser";
    }

    /**
     * 管理用户页面
     * */
    @GetMapping("userManagePage")
    public String userManagePage(Model model){
        return "user/userManage";
    }

    /**
     * 添加普通用户信息
     */
    @PostMapping("addUser")
    @ResponseBody
    public Result addUser(@RequestBody User user) {
        return userService.addUser(user);
    }


    /**
     * 修改普通用户信息
     */
    @PostMapping("updateUser")
    @ResponseBody
    public Result updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }


    /**
     * 删除科室信息
     */
    @GetMapping("deleteById")
    @ResponseBody
    public Result deleteById(Integer id) {
        return userService.deleteById(id);
    }



    /**
     * @param jsonObject 筛选条件对象
     * @deprecated 筛选查询普通用户接口
     */
    @PostMapping("queryUser")
    @ResponseBody
    public Result queryUser(@RequestBody JSONObject jsonObject) {
        return userService.queryUser(jsonObject);
    }


}
