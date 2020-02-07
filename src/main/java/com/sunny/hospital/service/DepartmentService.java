package com.sunny.hospital.service;

import com.sunny.hospital.dao.DepartmentDao;
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
 * @Description: 科室业务层
 * @Version 1.0
 */
@Service
public class DepartmentService {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private HospitalDao hospitalDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据医院查科室
     * */
    public List<Department> findAllByHospitalName(String hospitalName){
        List<Department> allByHospitalName = departmentDao.findAllByHospitalName(hospitalName);
        return allByHospitalName;
    }

    /**
     * 根据id查询科室
     * */
    public Department findById(Integer id){
        return departmentDao.findById(id);
    }
    /**
     * 添加科室
     */
    public Result addDepartment(Department department) {
        try {
            //获取要添加的科室名称
            String hisDepartmentName = department.getHisDepartmentName();

            //科室建档时间为当前时间（不可改变）
            department.setCreatedTime(new Date());

            //科室更新时间为当前时间
            department.setUpdatedTime(new Date());
            //判断前端传过来的科室名称是否不为空
            if (hisDepartmentName != null) {

                //取出医院编码
                String hospitalCode = department.getHospitalName();

                //调用dao层方法 通过科室名称和医院编号进行查询
                Department byHisDepartmentNameAndHospitalCode = departmentDao.findByHisDepartmentNameAndHospitalName(hisDepartmentName, hospitalCode);

                //判断该医院中存在相同的科室名称
                if (byHisDepartmentNameAndHospitalCode != null) {
                    return new Result(-1, "科室名称已存在，请不要重复添加");
                }


                //进行保存（前端传过来的科室信息数据）
                departmentDao.save(department);

                //返回保存的数据
                return new Result(0, "成功添加此科室", "");
            } else {
                return new Result(-1, "科室名称不能为空");
            }
        } catch (Exception e) {
            logger.error("【添加科室异常】" + department + e);
            return new Result(-1, "添加科室异常");
        }
    }


    /**
     * 修改科室信息
     */
    public Result updateDepartment(Department department) {
        try {
            //获取要修改的科室名称
            String hisDepartmentName = department.getHisDepartmentName();

            //取出要修改的科室id
            Integer id = department.getId();

            //判断修改后的科室名称不为空
            if (hisDepartmentName != null) {

                //通过科室id进行查询
                Department byId = departmentDao.findById(id);

                //如果修改后的科室名称和修改前的科室名称不一致
                if (!byId.getHisDepartmentName().equals(hisDepartmentName)) {

                    //取出医院编码
                    String hospitalCode = department.getHospitalName();

                    //判断该医院中是否存在相同科室名称
                    Department byHisDepartmentNameAndHospitalCode = departmentDao.findByHisDepartmentNameAndHospitalName(hisDepartmentName, hospitalCode);

                    //判断该医院中已存在相同科室名称
                    if (byHisDepartmentNameAndHospitalCode != null) {
                        return new Result(-1, "科室名称已存在，请不要重复添加");
                    }
                }
                //修改后保存当前时间
                department.setUpdatedTime(new Date());

                //保存前端传过来的所有信息
                departmentDao.save(department);

                //把信息传给前端
                return new Result(0, "科室信息修改成功", "");
            } else {
                return new Result(-1, "科室名称不能为空");
            }
        } catch (Exception e) {
            logger.error("【修改科室异常】" + department + e);
            return new Result(-1, "修改科室异常");
        }
    }


    /**
     * 通过科室id进行删除科室
     */
    public Result deleteById(Integer id) {
        try {
            //调用dao层的方法，通过科室id进行删除
            departmentDao.deleteById(id);
            return new Result(0, "成功删除此科室", "");
        } catch (Exception e) {
            logger.error("【删除科室异常】" + id + e);
            return new Result(-1, "删除科室异常");
        }
    }


    /**
     * 筛选查询医院科室列表
     */
    public Result queryDepartment(JSONObject jsonObject) {
        try {
            StringBuilder sb = new StringBuilder(); //创建拼接对象
            String hisDepartmentName = jsonObject.getString("hisDepartmentName");
            String hospitalName = jsonObject.getString("hospitalName");
            //拼接hospitalCode
            if (StringUtils.isNotEmpty(hospitalName)){
                sb.append("and  hospital_name='" + hospitalName + "'");
            }
            //判断科室名称是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(hisDepartmentName)) {
                sb.append(" and his_department_name like '%" + hisDepartmentName + "%'");
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
            String countSql = "select count(*) as count from department where id>0 " + sb.toString();
            Map<String, Object> countMap = jdbcTemplate.queryForMap(countSql);
            Integer allCount = Integer.valueOf(countMap.get("count").toString());
            //分页条件处理
            Pager pager = new Pager(allCount, pageSize, pageNum);
            int firstRow = pager.getFirstRow();
            int rowPerPage = pager.getRowPerPage();
            //查询数据
            StringBuilder dataSql = new StringBuilder();
            dataSql.append("select * from department where id>0 ");
            dataSql.append(sb);
            //按倒序方式排列并分页
            dataSql.append(" order by created_time desc limit " + firstRow + "," + rowPerPage);
            List<Department> query = jdbcTemplate.query(dataSql.toString(), new DepartmentRowMapper());
            return new Result(query, allCount);
        } catch (Exception e) {
            LOGGER.error("科室筛选查询异常" + e);
            return new Result<>(-1, "科室筛选查询异常");
        }
    }

}
