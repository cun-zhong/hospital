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

    /**
     * 筛选查询医院列表
     * */
    public Result queryHospital(JSONObject jsonObject){
        try {
            StringBuilder sb = new StringBuilder(); //创建拼接对象
            String hospitalCode = jsonObject.getString("hospitalCode");
            //判断医院编码是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(hospitalCode)){
               sb.append(" and hospital_code='"+hospitalCode+"'");
            }
            String hospitalName = jsonObject.getString("hospitalName");
            //判断医院名称是否为空 模糊查询
            if (StringUtils.isNotEmpty(hospitalName)){
                sb.append(" and hospital_name like '%"+hospitalName+"%'");
            }
            //分页条件 每页条数，当前页
            int pageNum,pageSize;
            if (jsonObject.get("pageNum")==null || jsonObject.get("pageSize")==null){
                pageNum=1;
                pageSize=10;
            }else {
                //当前页
                pageNum= jsonObject.getInt("pageNum");
                //每页条数
                pageSize = jsonObject.getInt("pageSize");
            }
            //查询总条数
            String countSql="select count(*) as count from hospital where id>0 "+sb.toString();
            Map<String, Object> countMap = jdbcTemplate.queryForMap(countSql);
            Integer allCount = Integer.valueOf(countMap.get("count").toString());
            //分页条件处理
            Pager pager=new Pager(allCount,pageSize,pageNum);
            int firstRow = pager.getFirstRow();
            int rowPerPage = pager.getRowPerPage();
            //查询数据
            StringBuilder dataSql=new StringBuilder();
            dataSql.append("select * from hospital where id>0 ");
            dataSql.append(sb);
            //按倒序方式排列并分页
            dataSql.append(" order by create_time desc limit "+firstRow+","+rowPerPage);
            List<Hospital> query = jdbcTemplate.query(dataSql.toString(), new HospitalRowMapper());
            return new Result(query,allCount);
        }catch (Exception e){
            LOGGER.error("医院筛选查询异常"+e);
            return new Result<>(-1,"医院筛选查询异常");
        }
    }

    /**
     * 添加医院
     * */
//    public Result addHospital(Hospital hospital){
//        if
//    }
}
