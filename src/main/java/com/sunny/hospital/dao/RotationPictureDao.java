package com.sunny.hospital.dao;

import com.sunny.hospital.entity.RotationPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/23
 * @Description:
 * @Version 1.0
 */
@Transactional
public interface RotationPictureDao extends JpaRepository<RotationPicture,Long> {
    //根据id进行查询
    RotationPicture findById(Integer id);

}
