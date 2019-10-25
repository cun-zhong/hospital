package com.sunny.hospital.service;

import com.sunny.hospital.dao.RotationPictureDao;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.entity.RotationPicture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: 轮播图片业务层
 * @Version 1.0
 */
@Service
public class RotationPictureService {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalService.class);

    @Autowired
    private RotationPictureDao rotationPictureDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询轮播图
     * */
    public RotationPicture findById(Integer id){
        return rotationPictureDao.findById(id);
    }

    /**
     * 修改轮播图片信息
     */
    public Result updateRotationPicture(RotationPicture rotationPicture) {
        try {
            rotationPictureDao.save(rotationPicture);
            return new Result(0, "修改轮播图片成功", "");
        } catch (Exception e) {
            logger.error("【修改轮播图片信息异常】" + rotationPicture + e);
            return new Result(-1, "修改轮播图片信息异常");
        }
    }


    /**
     * 查看所有轮播图片信息
     */
    public Result findAll() {
        List<RotationPicture> all = rotationPictureDao.findAll();
        return new Result(all);
    }

    public List<RotationPicture> findAllRotationPicture(){
        return rotationPictureDao.findAll();
    }
}
