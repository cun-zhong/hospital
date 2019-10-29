package com.sunny.hospital.service;

import com.sunny.hospital.dao.DepartmentDao;
import com.sunny.hospital.dao.DoctorDao;
import com.sunny.hospital.dao.HospitalDao;
import com.sunny.hospital.entity.*;
import com.sunny.hospital.utils.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: 医生业务层
 * @Version 1.0
 */
@Service
public class DoctorService {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private HospitalDao hospitalDao;

    @Autowired
    private DoctorDao doctorDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 根据id查医生
     * */
    public Doctor findById(Integer id){
        return doctorDao.findById(id);
    }

    /**
     * 添加医生信息
     */
    public Result addDoctor(Doctor doctor) {
        try {
            //设置当前时间为建档时间
            doctor.setCreatedTime(new Date());
            //设置当前时间为更新时间
            doctor.setUpdatedTime(new Date());
            //获取要传入的医生名称
            String name = doctor.getName();
            //判断新添加的医生姓名不能为空
            if (StringUtils.isNotEmpty(name)) {
                //保存前端传过来的数据
                doctorDao.save(doctor);
                return new Result(0, "成功添加此医生", "");
            } else {
                return new Result(-1, "医生姓名不能为空");
            }
        } catch (Exception e) {
            logger.error("【添加医生异常】" + doctor + e);
            return new Result(-1, "添加医生异常");
        }
    }


    /**
     * 修改医生信息
     */
    public Result updateDoctor(Doctor doctor) {
        try {

            //获取要传入的医生名称
            String name = doctor.getName();
            //判断新添加的医生姓名不能为空
            if (StringUtils.isNotEmpty(name)) {

                //设置当前时间为更新时间
                doctor.setUpdatedTime(new Date());
                //保存前端传过来的数据
                doctorDao.save(doctor);
                return new Result(0, "成功修改此医生", "");
            } else {
                return new Result(-1, "医生姓名不能为空");
            }
        } catch (Exception e) {
            logger.error("【修改医生异常】" + doctor + e);
            return new Result(-1, "修改医生异常");
        }
    }


    /**
     * 通过医生id进行删除医生信息
     */
    public Result deleteById(Integer id) {
        try {
            doctorDao.deleteById(id);
            return new Result(0, "成功删除此医生信息", "");
        } catch (Exception e) {
            logger.error("【删除医生异常】" + id + e);
            return new Result(-1, "删除医生异常");
        }
    }


    /**
     * 医生筛选查询
     */
    public Result queryDoctor(JSONObject jsonObject) {
        try {
            StringBuilder sb = new StringBuilder(); //创建拼接对象
            String hospitalName = jsonObject.getString("hospitalName");
            //判断医院名称是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(hospitalName)) {
                sb.append(" and hospital_name='" + hospitalName + "'");
            }
            String hisDepartmentName = jsonObject.getString("hisDepartmentName");
            //判断科室名称是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(hisDepartmentName)) {
                sb.append(" and his_department_name='" + hisDepartmentName + "'");
            }
            String name = jsonObject.getString("name");
            //判断医生名称是否为空 拼接查询条件    模糊查询
            if (StringUtils.isNotEmpty(name)) {
                sb.append(" and name like '%" + name + "%'");
            }

            //分页条件 每页条数，当前页
            int pageNum, pageSize;
            if (jsonObject.get("pageNum") == null || jsonObject.get("pageSize") == null) {
                pageNum = 1;
                pageSize = 10;
            } else {
                //当前页
                pageNum = jsonObject.getInt("pageNum");
                //每页条数
                pageSize = jsonObject.getInt("pageSize");
            }
            //查询总条数
            String countSql = "select count(*) as count from doctor where id>0 " + sb.toString();
            Map<String, Object> countMap = jdbcTemplate.queryForMap(countSql);
            Integer allCount = Integer.valueOf(countMap.get("count").toString());
            //分页条件处理
            Pager pager = new Pager(allCount, pageSize, pageNum);
            int firstRow = pager.getFirstRow();
            int rowPerPage = pager.getRowPerPage();

            //查询数据
            StringBuilder dataSql = new StringBuilder();
            dataSql.append("select * from doctor where id>0 ");
            dataSql.append(sb);
            //按倒序方式排列并分页
            dataSql.append(" order by created_time desc limit " + firstRow + "," + rowPerPage);
            List<Doctor> query = jdbcTemplate.query(dataSql.toString(), new DoctorRowMapper());
            return new Result(query, allCount);
        } catch (Exception e) {
            LOGGER.error("医生筛选查询异常" + e);
            return new Result<>(-1, "医生筛选查询异常");
        }
    }
}
