package com.sunny.hospital.dao;

import com.sunny.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/23
 * @Description:
 * @Version 1.0
 */
@Transactional
public interface HospitalDao extends JpaRepository<Hospital,Long> {
    //根据医院的id进行查询
    Hospital findById(Integer id);

    //根据医院名称进行查询
    Hospital findByHospitalName(String hospitalName);

    //根据医院编号进行查询
    Hospital findByHospitalCode(String hospitalCode);

    //根据医院id进行删除
    void deleteById(Integer id);


}
