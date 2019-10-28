package com.sunny.hospital.dao;

import com.sunny.hospital.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/23
 * @Description:
 * @Version 1.0
 */
@Transactional
public interface AdminUserDao extends JpaRepository<AdminUser,Long> {
    //根据管理员的id进行查询
    AdminUser findById(Integer id);

    //根据管理员姓名进行查询
    AdminUser findByAdminName(String adminName);

    //根据管理员id进行删除科室信息
    void deleteById(Integer id);

}
