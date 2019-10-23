package com.sunny.hospital.dao;

import com.sunny.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/23
 * @Description:
 * @Version 1.0
 */
public interface HospitalDao extends JpaRepository<Hospital,Long> {
}
