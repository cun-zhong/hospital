package com.sunny.hospital.controller;

import com.sunny.hospital.permission.bean.UserInfo;
import com.sunny.hospital.service.UserInfoService;
import com.sunny.hospital.utils.Constants;
import com.sunny.hospital.utils.VerifyCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/29 16:21
 * @Description: 初始控制器
 * @Version 1.0
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("login")
    public String loginAction() {
        //接收请求云跳转到登陆页面
        return "/login";
    }

    @GetMapping ({"","/","/index"})
    public String index(Model model,HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //返回账号信息
        if("anonymousUser".equals(principal)) {
            return "/login";
        }else {
            User user = (User)principal;
            UserInfo byUsername = userInfoService.findByUsername(user.getUsername());
            model.addAttribute("name",user.getUsername());
            HttpSession session=request.getSession();//获取session并将userName存入session对象
            session.setAttribute("user", byUsername);
            model.addAttribute("role",byUsername.getRoles().get(0).getName());
        }
        return "hospital/index";
    }



    /**
     * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
     */
    @GetMapping("/genCaptcha")
    public void genCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED, 4, null);
        //将验证码放到HttpSession里面
        request.getSession().setAttribute(Constants.VALIDATE_CODE, verifyCode);
        LOGGER.info("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");
        //设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 116, 36, 5, true, new Color(249,205,173), null, null);
        //写给浏览器
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }

}