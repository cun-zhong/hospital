package com.sunny.hospital.dao;

import com.sunny.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

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

    //查询所以医院
    List<Hospital> findAll();

    //查询排名前三的热门医院
    @Query(value = "select * from hospital  order by pay_num desc limit 3",nativeQuery=true)
    List<Hospital> findHot();


}
