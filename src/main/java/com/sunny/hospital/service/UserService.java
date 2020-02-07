package com.sunny.hospital.service;

import com.sunny.hospital.config.MyPasswordEncoder;
import com.sunny.hospital.dao.UserDao;
import com.sunny.hospital.entity.*;
import com.sunny.hospital.permission.bean.Role;
import com.sunny.hospital.permission.bean.UserInfo;
import com.sunny.hospital.permission.repository.RoleRepository;
import com.sunny.hospital.permission.repository.UserInfoRepository;
import com.sunny.hospital.utils.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 孙宇豪
 * @Date: 2019/10/16 16:21
 * @Description: 普通用户管理业务层
 * @Version 1.0
 */
@Service
public class UserService {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据用户名查用户
     * */
    public User findUserByName(String name){
        User byUsername = userDao.findByUsername(name);
        return byUsername;
    }

    /**
     * 查询单个用户
     * */
    public User findUserById(Integer id){
       return userDao.findById(id);
    }

    /**
     * 查询全部用户
     * */
    public List<User> findAll(){
        return userDao.findAll();
    }

    /**
     * 添加普通用户
     */
    public Result addUser(User user) {
        //给用户设置默认密码
        user.setPassword("123");
        try {
            //取出要添加的用户名
            String name = user.getUsername();
            //取出要添加的密码
            String password = user.getPassword();
            //判断要添加的用户名不为空
            if (StringUtils.isNotEmpty(name)) {
                //调用dao层方法查看是否数据库中已存在该用户名
                UserInfo byName = userInfoRepository.findByUsername(name);
                //如果不存在
                if (byName == null) {
                    //判断用户名是否不为空
                    if (StringUtils.isNotEmpty(password)) {
                        //保存建档时间
                        user.setCreatedTime(new Date());
                        //保存更新时间
                        user.setUpdatedTime(new Date());
                        //保存前端传的所有数据
                        User save = userDao.save(user);
                        //添加认证用户
                        UserInfo userInfo=new UserInfo();
                        userInfo.setId(save.getId());
                        userInfo.setUsername(name);
                        userInfo.setPassword(passwordEncoder.encode(password));
                        //获取就诊人角色对象
                        Role byRid = roleRepository.findByRid(3L);
                        List<Role> roles=new ArrayList<>();
                        roles.add(byRid);
                        userInfo.setRoles(roles);
                        userInfoRepository.save(userInfo);
                        //返回保存的数据
                        return new Result(0, "成功添加此普通用户", "");
                    } else {
                        return new Result(-1, "普通用户的密码不能为空");
                    }
                } else {
                    return new Result(-1, "用户名已存在,请重新输入");
                }
            } else {
                return new Result(-1, "普通用户名不能为空");
            }
        } catch (Exception e) {
            logger.error("【添加普通用户异常】" + user + e);
            return new Result(-1, "添加普通用户异常");
        }
    }


    /**
     * 修改普通用户信息
     */
    public Result updateUser(User user) {

        try {
            //取出修改后的用户名
            String name = user.getUsername();
            //取出要修改的普通用户id
            Integer id = user.getId();
            //判断修改后的用户名不为空
            if (StringUtils.isNotEmpty(name)) {
                //获取要修改的普通用户的id
                User byId = userDao.findById(id);
                //判断修改前后的用户名是否不一致
                if (!byId.getName().equals(name)) {
                    //通过修改后的用户名调用dao层进行查询
                    UserInfo byName = userInfoRepository.findByUsername(name);
                    //如果存在
                    if (byName != null) {
                        return new Result(-1, "用户名已存在,请重新输入");
                    }
                }
                //如果一致就继续保存
                user.setPassword(byId.getPassword());
                UserInfo userInfo = userInfoRepository.findByIdAndUsername(id,byId.getUsername());
                userInfo.setPassword(passwordEncoder.encode(byId.getPassword()));
                userInfoRepository.save(userInfo);
                user.setIntegral(byId.getIntegral());
                user.setUpdatedTime(new Date());
                userDao.save(user);
                //返回所有
                return new Result(0, "成功修改此普通用户", "");
            } else {
                return new Result(-1, "普通用户名不能为空");
            }
        } catch (Exception e) {
            logger.error("【修改普通用户异常】" + user + e);
            return new Result(-1, "修改普通用户异常");
        }
    }



    /**
     * 删除普通用户信息
     */
    public Result deleteById(Integer id) {
        try {
            //调用dao层的方法,通过id进行删除
            userDao.deleteById(id);
            return new Result(0, "成功删除此普通用户", "");
        }catch (Exception e){
            logger.error("【删除普通用户异常】" + id + e);
            return new Result(-1, "删除普通用户异常");
        }
    }


    /**
     * 筛选查询普通用户列表
     */
    public Result queryUser(JSONObject jsonObject){
        try {
            StringBuilder sb = new StringBuilder(); //创建拼接对象
            String name = jsonObject.getString("name");
            String idCard = jsonObject.getString("idCard");
            String mobile = jsonObject.getString("tel");
            //拼接普通用户用户名  可模糊查询
            if (StringUtils.isNotEmpty(name)){
                sb.append(" and name like '%" + name + "%'");
            }
            //拼接普通用户身份证号  可模糊查询
            if (StringUtils.isNotEmpty(idCard)) {
                sb.append(" and id_card like '%" + idCard + "%'");
            }
            //拼接普通用户手机号
            if (StringUtils.isNotEmpty(mobile)){
                sb.append("and  tel='" + mobile + "'");
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
            String countSql = "select count(*) as count from user where id>0 " + sb.toString();
            Map<String, Object> countMap = jdbcTemplate.queryForMap(countSql);
            Integer allCount = Integer.valueOf(countMap.get("count").toString());

            //分页条件处理
            Pager pager = new Pager(allCount, pageSize, pageNum);
            int firstRow = pager.getFirstRow();
            int rowPerPage = pager.getRowPerPage();
            //查询数据
            StringBuilder dataSql = new StringBuilder();
            dataSql.append("select * from user where id>0 ");
            dataSql.append(sb);

            //按倒序方式排列并分页
            dataSql.append(" order by created_time desc limit " + firstRow + "," + rowPerPage);
            List<User> query = jdbcTemplate.query(dataSql.toString(), new UserRowMapper());
            return new Result(query, allCount);
        }catch (Exception e){
            logger.error("【普通用户筛选查询异常】" + jsonObject + e);
            return new Result(-1, "普通用户筛选查询异常");
        }
    }

}
