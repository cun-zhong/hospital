package com.sunny.hospital.dao;

import com.sunny.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/23
 * @Description:
 * @Version 1.0
 */
@Transactional
public interface DoctorDao extends JpaRepository<Doctor,Long> {
    //根据医生id进行查询
    Doctor findById(Integer id);

    //根据医生id删除医生信息
    void deleteById(Integer id);

    //根据医生姓名进行查询
    Doctor findByName(String name);

}
