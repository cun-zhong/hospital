package com.sunny.hospital.service;

import com.sunny.hospital.dao.AdminUserDao;
import com.sunny.hospital.entity.AdminUser;
import com.sunny.hospital.entity.Result;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: 管理员业务层
 * @Version 1.0
 */
@Service
public class AdminUserService {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AdminUserDao adminUserDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 添加管理员
     */
    public Result addAdminUser(AdminUser adminUser) {
        try {
            //取出要添加的管理员用户名
            String adminName = adminUser.getAdminName();
            //取出要添加的管理员密码
            String adminPassword = adminUser.getAdminPassword();
            //判断要添加的管理员用户名不为空
            if (StringUtils.isNotEmpty(adminName)) {
                //调用dao层方法查看是否数据库中已存在该用户名
                AdminUser byAdminName = adminUserDao.findByAdminName(adminName);
                //如果不存在
                if (byAdminName == null) {
                    //判断要添加的管理员密码是否不为空
                    if (StringUtils.isNotEmpty(adminPassword)) {
                        //保存建档时间
                        adminUser.setCreatedTime(new Date());
                        //保存更改时间
                        adminUser.setUpdatedTime(new Date());
                        //保存前端传过来的所有数据
                        adminUserDao.save(adminUser);
                        //返回保存的数据
                        return new Result(0, "成功添加此管理员", "");
                    } else {
                        return new Result(-1, "管理员密码不能为空");
                    }
                } else {
                    return new Result(-1, "管理员名称已存在,请重新输入");
                }
            } else {
                return new Result(-1, "管理员名称不能为空");
            }
        } catch (Exception e) {
            logger.error("【添加管理员异常】" + adminUser + e);
            return new Result(-1, "添加管理员异常");
        }
    }


    /**
     * 修改管理员信息
     */
    public Result updateAdminUser(AdminUser adminUser) {
        try {
            //取出修改后的管理员用户名
            String adminName = adminUser.getAdminName();
            //取出要修改的管理员id
            Integer id = adminUser.getId();
            //判断修改后的管理员用户名不为空
            if (StringUtils.isNotEmpty(adminName)) {
                //根据管理员id进行查询
                AdminUser byId = adminUserDao.findById(id);
                //如果修改后的管理员用户名称和修改前的不一致
                if (!byId.getAdminName().equals(adminName)) {
                    //调用dao层，查询数据库中是否存在此用户名
                    AdminUser byAdminName = adminUserDao.findByAdminName(adminName);
                    //如果库里存在此用户名
                    if (byAdminName != null) {
                        return new Result(-1, "管理员名称已存在,请重新输入");
                    }
                }
                String adminPassword = adminUser.getAdminPassword();

                if (StringUtils.isEmpty(adminPassword)) {
                    return new Result(-1, "普通用户密码不能为空");
                }
                //保存更新时间
                adminUser.setUpdatedTime(new Date());
                //保存所有
                adminUserDao.save(adminUser);
                //返回所有
                return new Result(0, "成功修改此管理员", "");
            } else {
                return new Result(-1, "管理员用户名不能为空");
            }
        } catch (Exception e) {
            logger.error("【修改管理员信息异常】" + adminUser + e);
            return new Result(-1, "修改管理员信息异常");
        }
    }


    /**
     * 删除管理员信息
     */
    public Result deleteById(Integer id) {
        try {
            //调用dao层方法通过管理员id进行删除
            adminUserDao.deleteById(id);
            return new Result(0, "成功删除此管理员", "");
        } catch (Exception e) {
            logger.error("【删除管理员信息异常】" + id + e);
            return new Result(-1, "删除管理员信息异常");
        }
    }
}
