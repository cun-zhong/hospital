package com.sunny.hospital.controller;

import com.sunny.hospital.config.MyPasswordEncoder;
import com.sunny.hospital.entity.Department;
import com.sunny.hospital.entity.Doctor;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.entity.User;
import com.sunny.hospital.permission.bean.UserInfo;
import com.sunny.hospital.service.DoctorService;
import com.sunny.hospital.service.UserInfoService;
import com.sunny.hospital.service.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DoctorService doctorService;

    /**
     * 修改密码页面
     * */
    @GetMapping("updatePasswordPage")
    public String updatePassWordPage(){
        return "user/password";
    }

    /**
     * @deprecated 修改密码接口
     * */
    @PostMapping("updatePassword")
    @ResponseBody
    public Result updatePassword(@RequestBody JSONObject jsonObject, HttpSession session){
        try {
            //从缓存中获取User对象
            UserInfo user = (UserInfo) session.getAttribute("user");
            //判断旧密码是否相同
            String oldPassword = jsonObject.getString("oldPassword");
            String password = jsonObject.getString("password");
            if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(password)){
                return new Result(-1,"密码不能为空");
            }
            if (!passwordEncoder.encode(oldPassword).equals(user.getPassword())){
                return new Result(-1,"密码不正确");
            }
            //加密密码
            user.setPassword(passwordEncoder.encode(password));
            //保存密码到验证对象中
            userInfoService.save(user);
            //获取用户角色
            String role = user.getRoles().get(0).getName();
            Integer id = user.getId();
            if (role.equals("doctor")){
                //如果是医生
                Doctor byId = doctorService.findById(id);
                byId.setPassword(password);
                doctorService.updateDoctor(byId);
            }else if (role.equals("user")){
                //如果是用户
                User userById = userService.findUserById(id);
                userById.setPassword(password);
                userService.updateUser(userById);
            }
            //管理员不用管 管理员天生存在与用户验证对象中
            return new Result(0,"修改成功");
        }catch (Exception e){
            return new Result(-1,"修改密码失败");
        }
    }

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
     * 删除
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
