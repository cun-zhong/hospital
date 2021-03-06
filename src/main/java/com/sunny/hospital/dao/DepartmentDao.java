package com.sunny.hospital.dao;


import com.sunny.hospital.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/23
 * @Description:
 * @Version 1.0
 */
@Transactional
public interface DepartmentDao extends JpaRepository<Department,Long> {
    //根据科室的id进行查询
    Department findById(Integer id);

    //根据科室名称进行查询
    Department findByHisDepartmentName(String hisDepartmentName);

    //根据科室名称和医院编码进行查找
    Department findByHisDepartmentNameAndHospitalName(String hisDepartmentName,String hospitalName);

    //根据科室id进行删除科室信息
    void deleteById(Integer id);

    //根据医院名称查科室
    List<Department> findAllByHospitalName(String name);


}
