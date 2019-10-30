package com.sunny.hospital.service;


import com.sunny.hospital.dao.HospitalDao;
import com.sunny.hospital.entity.Hospital;
import com.sunny.hospital.entity.HospitalRowMapper;
import com.sunny.hospital.entity.Result;
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
 * @Description: 医院业务层
 * @Version 1.0
 */
@Service
public class HospitalService {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HospitalDao hospitalDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询所有医院
     * */
    public List<Hospital> findAll(){
       return hospitalDao.findAll();
    }

    /**
     * 根据医院id查询医院
     *
     * @param id
     * @return
     */
    public Hospital findbyId(Integer id) {
        //调用dao层的findById方法进行查询
        return hospitalDao.findById(id);
    }


    /**
     * 根据医院姓名查询医院
     *
     * @param hospitalName
     * @return
     */
    public Hospital findbyhospitalName(String hospitalName) {
        //调用dao层的findByHospitalName方法进行查询
        return hospitalDao.findByHospitalName(hospitalName);
    }


    /**
     * 根据医院编号查询医院
     *
     * @param hospitalCode
     * @return
     */
    public Hospital findByhospitalCode(String hospitalCode) {
        //调用dao层的findByHospitalCode方法进行查询
        return hospitalDao.findByHospitalCode(hospitalCode);
    }


    /**
     * 筛选查询医院列表
     */
    public Result queryHospital(JSONObject jsonObject) {
        try {
            StringBuilder sb = new StringBuilder(); //创建拼接对象
            String hospitalCode = jsonObject.getString("hospitalCode");
            //判断医院编码是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(hospitalCode)) {
                sb.append(" and hospital_code='" + hospitalCode + "'");
            }
            String hospitalName = jsonObject.getString("hospitalName");
            //判断医院名称是否为空 模糊查询
            if (StringUtils.isNotEmpty(hospitalName)) {
                sb.append(" and hospital_name like '%" + hospitalName + "%'");
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
            String countSql = "select count(*) as count from hospital where id>0 " + sb.toString();
            Map<String, Object> countMap = jdbcTemplate.queryForMap(countSql);
            Integer allCount = Integer.valueOf(countMap.get("count").toString());
            //分页条件处理
            Pager pager = new Pager(allCount, pageSize, pageNum);
            int firstRow = pager.getFirstRow();
            int rowPerPage = pager.getRowPerPage();
            //查询数据
            StringBuilder dataSql = new StringBuilder();
            dataSql.append("select * from hospital where id>0 ");
            dataSql.append(sb);
            //按倒序方式排列并分页
            dataSql.append(" order by create_time desc limit " + firstRow + "," + rowPerPage);
            List<Hospital> query = jdbcTemplate.query(dataSql.toString(), new HospitalRowMapper());
            return new Result(query, allCount);
        } catch (Exception e) {
            LOGGER.error("医院筛选查询异常" + e);
            return new Result<>(-1, "医院筛选查询异常");
        }
    }


    /**
     * 添加医院
     */
    public Result addHospital(Hospital hospital) {
        try {
            //取出医院名称
            String hospitalName = hospital.getHospitalName();
            //判断医院名称是否为空
            if (hospitalName != null) {
                //调用dao层查询数据库中是否存在此医院名称
                Hospital byHospitalName = hospitalDao.findByHospitalName(hospitalName);
                //判断数据库中是否存在此医院名称
                if (byHospitalName == null) {
                    //取出医院编码
                    String hospitalCode = hospital.getHospitalCode();
                    //判断医院编码是否为空
                    if (hospitalCode != null) {
                        //调用dao层查询数据库中是否存在此医院编码
                        Hospital byHospitalCode = hospitalDao.findByHospitalCode(hospitalCode);
                        //判断数据库中是否存在此医院编码
                        if (byHospitalCode == null) {
                            //医院建档时间为当前时间（不可改变）
                            hospital.setCreateTime(new Date());
                            //医院信息更新时间为当前时间
                            hospital.setUpdateTime(new Date());
                            //进行保存（前端传过来医院信息的数据）
                            Hospital save = hospitalDao.save(hospital);
                            //返回保存的信息
                            return new Result(0, "新增医院成功", "");
                        } else {
                            return new Result(-1, "医院编码已存在");
                        }
                    } else {
                        return new Result(-1, "医院编码不能为空");
                    }
                } else {
                    return new Result(-1, "医院名称已存在");
                }
            } else {
                return new Result(-1, "医院名称不能为空");
            }
        } catch (Exception e) {
            logger.error("【添加医院异常】" + hospital + e);
            return new Result(-1, "添加医院异常");
        }
    }


    /**
     * 修改医院信息
     */
    public Result updateHospital(Hospital hospital) {
     try {
            //取出更改后的医院名称
            String hospitalName = hospital.getHospitalName();

            //取出医院id
            Integer id = hospital.getId();

            //通过id进行查询
            Hospital byId = hospitalDao.findById(id);

            //判断如果修改后的名称和修改前的名称不一致
            if (!byId.getHospitalName().equals(hospitalName)) {

                //通过修改后的名称进行查询数据库中是否存在
                Hospital byHospitalName = hospitalDao.findByHospitalName(hospitalName);

                //取出修改后的医院编码
                String hospitalCode = hospital.getHospitalCode();

                //判断原医院编码和修改后的医院编码不一致
                if (!byId.getHospitalCode().equals(hospitalCode)) {

                    //通过医院编码查数据库中是否存在此编码
                    Hospital byHospitalCode = hospitalDao.findByHospitalCode(hospitalCode);

                    //如果数据库存在此医院编码
                    if (byHospitalCode != null) {
                        return new Result(-1, "当前医院编码已存在");
                    }
                }

                //如果数据库存在此医院名称
                if (byHospitalName != null) {
                    return new Result(-1, "当前医院名称已存在");
                }
            }
            //修改后保存当前时间
            hospital.setUpdateTime(new Date());
            //保存前端传过来的所有信息
            Hospital save = hospitalDao.save(hospital);
            //返回保存的信息
            return new Result(0, "修改医院信息成功", "");
        } catch (Exception e) {
            logger.error("【修改医院信息异常】" + hospital + e);
            return new Result(-1, "修改医院信息异常");
        }
    }


    /**
     * 删除医院信息
     */
    public Result deleteHospital(Integer id) {
        try {
            //根据医院id进行删除
            hospitalDao.deleteById(id);
            return new Result(0, "成功删除此医院", "");
        } catch (Exception e) {
            logger.error("【删除医院信息异常】" + id + e);
            return new Result(-1, "删除医院信息异常");
        }
    }

}
