package com.sunny.hospital.service;

import com.sunny.hospital.dao.BookingOrderDao;
import com.sunny.hospital.dao.DoctorDao;
import com.sunny.hospital.dao.HospitalDao;
import com.sunny.hospital.dao.UserDao;
import com.sunny.hospital.entity.*;
import com.sunny.hospital.utils.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private HospitalDao hospitalDao;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询医生待就诊号
     * */
    public List<BookingOrder> findAllByDoctorIdAndStatus(Integer status, Integer doctorId){
        return bookingOrderDao.findAllByDoctorIdAndStatus(doctorId, status);
    }

    /**
     * 添加预约挂号订单信息
     */
    public Result addBookingOrder(BookingOrder bookingOrder) {
        try {
            //获取当前登录用户的信息
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //获取登录用户名
            String username = user.getUsername();
            //根据用户名查询用户对象
            com.sunny.hospital.entity.User userByName = userDao.findByUsername(username);
            //判断当前用户积分是否小于0，如果小于0.则返回相关提示信息
            if (userByName.getIntegral()<0){
                return new Result(-1,"信用分小于0，您已被禁止使用挂号功能");
            }
            //获取用户id
            Integer userid = userByName.getId();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //创建选择日期
            Date chooseDate = null;
            try {
                //转换前台选择的挂号时间
                chooseDate = sdf.parse(bookingOrder.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //判断选择时间为空和选择日期小于当前时间的则返回相关提示信息
            if (chooseDate != null && chooseDate.getTime() <= new Date().getTime()){
                return new Result(-1,"选择的日期小于当前时间");
            }
            //获取预约挂号订单上医生的id
            Integer doctorId = bookingOrder.getDoctorId();
            //获取预约挂号订单上预约的是时间段
            String timeRange = bookingOrder.getTimeRange();
            //医生在该时间段所以挂号信息
            List<BookingOrder> allList = bookingOrderDao.findAllByDoctorIdAndChooseDateAndTimeRange(doctorId, chooseDate, timeRange);

            //待就诊信息
            List<BookingOrder> waitList = bookingOrderDao.findAllByDoctorIdAndChooseDateAndTimeRangeAndStatus(doctorId, chooseDate, timeRange, 0);
            //通过doctorId调用dao层 查询医生
            Doctor doctor = doctorDao.findById(doctorId);
            //设置每小时医生号源个数默认为5
            int hourPeople=5;
            if (doctor != null && doctor.getHourPeople() != null){
                //如果医生设置了每个小时就诊人数，则以设置为准
                hourPeople = doctor.getHourPeople();
            }
            //如果预约的号源数大于或者等于改医生的每小时最大号源数则返回相关提示信息
            if (waitList.size() >= hourPeople){
                return new Result(-1,"医生"+doctor.getName()+"该时间段已无号");
            }

            //判断午别
            String am="1";
            if (Integer.parseInt(timeRange) <= 4){
                am="0";
            }
            //用户当前半天的挂号数
            List<BookingOrder> selfList = bookingOrderDao.findByDoctorIdAndAmAndChooseDateAndUserIdAndStatus(doctorId, am, chooseDate, userid, 0);
            //如果挂号数不为空并且大于0 则返回相关提示信息
            if (selfList != null && selfList.size() > 0){
                return new Result(-1,"您在当前半天内已存在挂号信息");
            }

            int sort=1;//号源序号
            if (allList != null && allList.size() > 0){
                //如果已有人挂号，那么去最后一个人的序号加1
                sort = allList.get(allList.size()-1).getSort() + 1;
            }

            //新建挂号对象
            BookingOrder order=new BookingOrder();
            //设置挂号对象的相关信息
            //设置userid
            order.setUserId(userid);
            //设置医生id
            order.setDoctorId(doctorId);
            //设置医生名称
            order.setDoctorName(doctor.getName());
            //设置医生职称
            order.setDoctorTitle(doctor.getTitle());
            //设置医院名称
            order.setHospitalName(doctor.getHospitalName());
            //设置科室名称
            order.setHisDepartmentName(doctor.getHisDepartmentName());
            //设置预约成功的时间
            order.setRegisterTime(new Date());
            //设置预约日期
            order.setChooseDate(chooseDate);
            //设置上午/下午
            order.setAm(am);
            //设置时间段
            order.setTimeRange(timeRange);
            //设置状态
            order.setStatus(0);
            //设置序号
            order.setSort(sort);
            //设置时间段—序号
            order.setRangeSort(timeRange+"-"+sort);
            //设置就诊人姓名
            order.setPatientName(userByName.getName());
            order.setCreatedTime(new Date());
            order.setUpdatedTime(new Date());
            //进行保存
            bookingOrderDao.save(order);

            //为用户增加信用分
            userByName.setIntegral(userByName.getIntegral()+1);
            //进行保存
            userDao.save(userByName);

            //为医院增加订单量
            Hospital hospital = hospitalDao.findByHospitalName(doctor.getHospitalName());
            //订单量+1
            hospital.setPayNum(hospital.getPayNum()+1);
            //保存
            hospitalDao.save(hospital);

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
            String hisDoctorName = jsonObject.getString("doctorName");
            String forDateDay = jsonObject.getString("chooseDate");
            //用户id
            String userId = jsonObject.getString("userId");
            if (StringUtils.isNotEmpty(userId)){
                sb.append(" and user_id="+userId);
            }
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
                sb.append(" and doctor_name='" + hisDoctorName + "'");
            }
            //判断预约日期是否为空 拼接查询条件
            if (StringUtils.isNotEmpty(forDateDay)) {
                sb.append(" and choose_date='" + forDateDay + "'");
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

    public Result cancel(Integer id){
        try {
            BookingOrder order = bookingOrderDao.findById(id);
            //判断号源状态是不是待就诊状态
            if (order.getStatus()!=0){
                return new Result(-1,"该号源已处理");
            }
            //获取就诊日期
            Date chooseDate = order.getChooseDate();
            //就诊午别
            String am = order.getAm();
            //当前时间
            Date now=new Date();
            //计算规定退号时间
            Calendar c=Calendar.getInstance();
            c.setTime(chooseDate);
            if ("0".equals(am)){
                //上午截止12点
                c.set(Calendar.HOUR_OF_DAY,12);
            }else {
                //下午截止18点
                c.set(Calendar.HOUR_OF_DAY,18);
            }
            //判断是否超出规定退号时间
            if (now.getTime() > c.getTimeInMillis()){
                return new Result(-1,"已超出规定退号时间，无法退号");
            }
            //退号减分
            addScore(-2);
            //修改订单状态
            order.setStatus(2);
            updateBookingOrder(order);
            return new Result(0,"取消挂号成功");
        }catch (Exception e){
            logger.error("取消挂号异常");
        }
        return new Result(-1,"取消挂号失败");
    }

    /***
     * @deprecated 操作用户分数的方法
     */
    public void addScore(Integer score){
        //获取当前登录用户的信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取登录用户名
        String username = user.getUsername();
        com.sunny.hospital.entity.User userInfo = userDao.findByUsername(username);
        Integer integral = userInfo.getIntegral();
        userInfo.setIntegral(integral+score);
        userInfo.setUpdatedTime(new Date());
        userDao.save(userInfo);
    }


}
