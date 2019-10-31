package com.sunny.hospital.dao;


import com.sunny.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/23
 * @Description:
 * @Version 1.0
 */
@Transactional
public interface UserDao extends JpaRepository<User,Long> {
    //根据就诊人的id进行查询
    User findById(Integer id);

    //根据就诊人id进行删除科室信息
    void deleteById(Integer id);

    //根据用户名进行查询
    User findByUsername(String name);
}
