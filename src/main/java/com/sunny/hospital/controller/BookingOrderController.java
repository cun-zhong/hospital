package com.sunny.hospital.controller;

import com.sunny.hospital.entity.BookingOrder;
import com.sunny.hospital.entity.Doctor;
import com.sunny.hospital.entity.Result;
import com.sunny.hospital.service.BookingOrderService;
import com.sunny.hospital.service.DoctorService;
import com.sunny.hospital.service.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/29
 * @Description: 预约挂号
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "booking")
public class BookingOrderController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private BookingOrderService bookingOrderService;

    @Autowired
    private UserService userService;

    /**
     * @deprecated 用户订单查询页面
     * */
    @GetMapping("bookingOrderPage")
    public String bookingOrderPage(){
        return "booking/bookingOrderPage";
    }

    /**
     * @deprecated 筛选订单接口
     * */
    @PostMapping("queryBookingOrder")
    @ResponseBody
    public Result queryBookingOrder(@RequestBody JSONObject jsonObject){
        //获取当前登录用户的信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取登录用户名
        String username = user.getUsername();
        com.sunny.hospital.entity.User userByName = userService.findUserByName(username);
        jsonObject.put("userId",userByName.getId());
        jsonObject.put("doctorName","");
        Result result = bookingOrderService.queryBookingOrder(jsonObject);
        return result;
    }

    /**
     * @deprecated 挂号接口
     * */
    @PostMapping("register")
    @ResponseBody
    public Result register(@RequestBody BookingOrder bookingOrder){
        if (bookingOrder!=null){
            return bookingOrderService.addBookingOrder(bookingOrder);
        }else {
            return new Result(-1,"查询条件不能为空");
        }
    }

    /**
     * @deprecated 挂号页面
     * */
    @GetMapping("bookingPage")
    public String bookingPage(){
        return "booking/bookingPage";
    }

    /**
     * @deprecated  查询医生信息页面
     * */
    @GetMapping("registerPage")
    public String registerPage(Integer id, Model model){
        Doctor byId = doctorService.findById(id);
        model.addAttribute("doctor",byId);
        return "booking/registerPage";
    }

    /**
     * @deprecated  查询医生信息页面
     * */
    @GetMapping("findDoctorDetail")
    @ResponseBody
    public Result findDoctorDetail(Integer id){
        Doctor doctor = doctorService.findById(id);
        Map<String,Object> data=new HashMap<>();
        data.put("doctor",doctor);
        List<Map<String,Object>> returnList = new ArrayList<>();
        //医生不为空
        if(null != doctor && StringUtils.isNotBlank(doctor.getTime())){
            //查询医生所有待就诊信息
            List<BookingOrder> list = bookingOrderService.findAllByDoctorIdAndStatus(doctor.getId(),0);
            //如果查询到该医生的班表信息
            //去掉存储在该字段的最后一个','以便分隔
            String time = doctor.getTime().substring(0,doctor.getTime().length()-1);
            String[] timeArr = time.split(",");
            //获取当前周几
            Date now = new Date();
            //13位时间戳
            long nowTime = now.getTime();
            int week = now.getDay();//0-周日，6-周六
            //判断从现在开始的一周 哪些是本周哪些是下周
            List<String> thisWeek = new ArrayList<>();
            List<String> nextWeek = new ArrayList<>();
            for (String temp:timeArr) {
                //如果temp是8，即为周四下午。今天如果是周四，2*week=8。那么这个周四算在下周
                if (Integer.parseInt(temp) <= 2*week){
                    nextWeek.add(temp);
                }else{
                    thisWeek.add(temp);
                }
            }
            thisWeek.addAll(nextWeek);//此时thisWeek为重新排列过的星期

            //遍历从今天开始的一周排班
            for (String temp:thisWeek) {
                //医生门诊时间代表的数字
                int intTemp = Integer.parseInt(temp);
                //判断门诊时间数是否是能被2整除
                int intTemp1 = intTemp%2==0?intTemp:intTemp+1;
                //判断门诊时间数代表上午还是下午 0上午 1下午
                String am = intTemp%2==0?"1":"0";
                //12*60*60*1000 毫秒转换为小时
                Date date = null;
                //计算排班数字所代表的的具体日期
                if (Integer.parseInt(temp) > 2*week){
                    date = new Date(nowTime+(intTemp1-2*week)*12*60*60*1000);
                }else{
                    date = new Date(nowTime+(intTemp1-2*week+14)*12*60*60*1000);
                }

                List<Map<String,Object>> resList = new ArrayList<>();
                //遍历该医生所有的挂号信息
                if (list != null && list.size() > 0){
                    int people1 =0,people2 =0,people3 =0,people4 =0;
                    for (BookingOrder register:list) {
                        //判断预约日期小于等于排班日期
                        if (register.getChooseDate().getTime() <= date.getTime() && register.getChooseDate().getTime()+24*60*60*1000 > date.getTime()){
                            //介于当天0点与24点之间
                            if ("0".equals(am)){//上午
                                if ("1".equals(register.getTimeRange())){
                                    people1++;
                                }else if ("2".equals(register.getTimeRange())){
                                    people2++;
                                }else if ("3".equals(register.getTimeRange())){
                                    people3++;
                                } else if ("4".equals(register.getTimeRange())){
                                    people4++;
                                }
                            }else{//下午
                                if ("5".equals(register.getTimeRange())){
                                    people1++;
                                }else if ("6".equals(register.getTimeRange())){
                                    people2++;
                                }else if ("7".equals(register.getTimeRange())){
                                    people3++;
                                } else if ("8".equals(register.getTimeRange())){
                                    people4++;
                                }
                            }
                        }
                    }
                    //获取医生每小时就诊人数上限，没设置就取默认值5
                    int hourPeople = doctor.getHourPeople() ==null?5:doctor.getHourPeople();
                    if (people1 < hourPeople){
                        Map<String,Object> map = new HashMap<>();
                        map.put("1",true);
                        resList.add(map);
                    }else{
                        Map<String,Object> map = new HashMap<>();
                        map.put("1",false);
                        resList.add(map);
                    }

                    if (people2 < hourPeople){
                        Map<String,Object> map = new HashMap<>();
                        map.put("2",true);
                        resList.add(map);
                    }else{
                        Map<String,Object> map = new HashMap<>();
                        map.put("2",false);
                        resList.add(map);
                    }

                    if (people3 < hourPeople){
                        Map<String,Object> map = new HashMap<>();
                        map.put("3",true);
                        resList.add(map);
                    }else{
                        Map<String,Object> map = new HashMap<>();
                        map.put("3",false);
                        resList.add(map);
                    }

                    if (people4 < hourPeople){
                        Map<String,Object> map = new HashMap<>();
                        map.put("4",true);
                        resList.add(map);
                    }else{
                        Map<String,Object> map = new HashMap<>();
                        map.put("4",false);
                        resList.add(map);
                    }

                }else if(list != null && list.size() == 0){
                    //没有挂号信息
                    for (int i = 1; i < 5; i++) {
                        Map<String,Object> map = new HashMap<>();
                        map.put(String.valueOf(i),true);
                        resList.add(map);
                    }
                }
                Map<String,Object> map = new HashMap<>();
                map.put(temp,resList);
                returnList.add(map);
            }
        }
        data.put("result",returnList);
        return new Result(data);
    }

    /**
     * @deprecated  取消挂号接口
     * */
    @GetMapping("cancel")
    @ResponseBody
    public Result cancel(Integer id){
        Result cancel = bookingOrderService.cancel(id);
        return cancel;
    }
}
