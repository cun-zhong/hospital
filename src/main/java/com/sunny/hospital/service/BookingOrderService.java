package com.sunny.hospital.service;

import com.sunny.hospital.dao.BookingOrderDao;
import com.sunny.hospital.entity.*;
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
 * @Description: 挂号订单业务层
 * @Version 1.0
 */
@Service
public class BookingOrderService {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalService.class);

    @Autowired
    private BookingOrderDao bookingOrderDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 添加预约挂号订单信息
     */
    public Result addBookingOrder(BookingOrder bookingOrder) {
        try {
            bookingOrderDao.save(bookingOrder);
            return new Result(0, "预约挂号成功", "");
        } catch (Exception e) {
            logger.error("【预约挂号异常】" + bookingOrder + e);
            return new Result(-1, "预约挂号异常");
        }
    }


    /**
     * 修改预约挂号订单信息
     */
    public Result updateBookingOrder(BookingOrder bookingOrder){
        try {
            bookingOrderDao.save(bookingOrder);
            return new Result(0, "修改预约挂号成功", "");
        }catch (Exception e){
            logger.error("【修改预约挂号异常】" + bookingOrder + e);
            return new Result(-1, "修改预约挂号异常");
        }
    }



    /**
     * 预约挂号订单筛选查询
     */
    public Result queryBookingOrder(JSONObject jsonObject){
        try {
            StringBuilder sb = new StringBuilder(); //创建拼接对象
            String hospitalName = jsonObject.getString("hospitalName");
            String hisDepartmentName = jsonObject.getString("hisDepartmentName");
            String hisDoctorName = jsonObject.getString("hisDoctorName");
            String forDateDay = jsonObject.getString("forDateDay");

            //判断医院名称是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(hospitalName)) {
                sb.append(" and hospital_name='" + hospitalName + "'");
            }
            //判断科室名称是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(hisDepartmentName)) {
                sb.append(" and his_department_name='" + hisDepartmentName + "'");
            }
            //判断医生姓名是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(hisDoctorName)) {
                sb.append(" and his_doctor_name='" + hisDoctorName + "'");
            }
            //判断预约日期是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(forDateDay)) {
                sb.append(" and for_date_day='" + forDateDay + "'");
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
            String countSql = "select count(*) as count from booking_order where id>0 " + sb.toString();
            Map<String, Object> countMap = jdbcTemplate.queryForMap(countSql);
            Integer allCount = Integer.valueOf(countMap.get("count").toString());
            //分页条件处理
            Pager pager = new Pager(allCount, pageSize, pageNum);
            int firstRow = pager.getFirstRow();
            int rowPerPage = pager.getRowPerPage();


            //查询数据
            StringBuilder dataSql = new StringBuilder();
            dataSql.append("select * from booking_order where id>0 ");
            dataSql.append(sb);
            //按倒序方式排列并分页
            dataSql.append(" order by created_time desc limit " + firstRow + "," + rowPerPage);
            List<BookingOrder> query = jdbcTemplate.query(dataSql.toString(), new BookingOrderRowMapper());
            return new Result(query, allCount);
        }catch (Exception e){
            LOGGER.error("预约挂号订单筛选查询异常" + e);
            return new Result<>(-1, "预约挂号订单筛选查询异常");
        }
    }

}
