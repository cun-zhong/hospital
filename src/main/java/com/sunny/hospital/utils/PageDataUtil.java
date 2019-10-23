package com.sunny.hospital.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 孙宇豪
 * @Date: 2018/11/23 16:49
 * @Description:   封装翻页返回值
 * @return
 * @Version 1.0
 */
public class PageDataUtil {
    /**
     *   封装翻页返回值
     */
    public static Map<String, Object> page(Integer pageNum, int pageSize, int allCont, List list) {
        Map<String, Object> map = new HashMap<>();
        /**
         * 计算得到分页应该需要分几页，其中不满一页的数据按一页计算
         * */
        int pageCount =0;//2\总页 数
        if (allCont % pageSize != 0) {
            pageCount = allCont / pageSize + 1;
        } else {
            pageCount = allCont / pageSize;
        }
        map.put("pageNum", pageNum);//当前页
        map.put("rowCount", allCont);//总条数
        map.put("pageCount", pageCount);//总页数
        map.put("data", list);
        return map;
    }
}
