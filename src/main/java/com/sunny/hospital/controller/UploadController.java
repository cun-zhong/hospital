package com.sunny.hospital.controller;



import com.sunny.hospital.utils.FileUtil;
import com.sunny.hospital.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * @author 孙宇豪
 * @Description: TODO 上传接口
 * @date 2019/06/3
 */
@SuppressWarnings("ALL")
@CrossOrigin
@RestController
@RequestMapping("upload")
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String uploadUrl="http://localhost";

    /**
     * 上传文件
     *
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Result uploadVideo(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            long s1 = System.currentTimeMillis();
            //从文件流中获取文件名称
            String fileName = file.getOriginalFilename();
            logger.info("文件名称：" + fileName);
            //判断文件类型 mp4 mp3 doc等
            String[] split = fileName.split("\\.");
            String filetype=split[split.length-1];
            //用UUID给文件一个随机名称
            fileName = UUID.randomUUID().toString() +"."+ filetype;
            String server = request.getServerName();//当前服务器地址
            int port = request.getServerPort();//当前服务器端口
            //文件地址
            String filePath = "";
            if (server.equals("localhost") || server.equals("127.0.0.1")) {
                filePath = System.getProperty("catalina.home") + "/webapps/images/";// 线下
            }else {
                filePath = System.getProperty("catalina.home") + "/webapps/images/";// 线上
            }
            /*
             * 文件路径如果部署在本地 可以直接从硬盘上访问
             */
            filePath="D://images/";
//            filePath="/filelist/";
            try {
                //调用上传文件工具类将文件传到指定地址
                FileUtil.uploadFile(file.getBytes(), filePath,  fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long s3 = System.currentTimeMillis();
            logger.info("上传文件用时" + (s3 - s1) + "ms");
            logger.info("文件地址："+filePath+fileName);
            //为了便于本地访问 在tomcat上配置了虚拟路径/filelist 所以返回给前端的是虚拟路径
            return new Result("/filelist/"+fileName);
        } catch (Exception el) {
            logger.error("上传文件异常"+el);
            return new Result(-1, "上传失败!");
        }
    }


}
