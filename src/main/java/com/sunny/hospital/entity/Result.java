package com.sunny.hospital.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author <a href="mailto:zhuangzhi.liu@thistech.com">Zhuangzhi Liu</a>
 *         Created on:  01/06/2017.
 */
@Getter
@Setter
@ToString
public class Result<T> {
    private T data;
    private boolean success = true;
    private int code;
    private String msg;
    private Integer count;

    public Result(T result,Integer count){
        if (result == null){
            this.success = false;
            this.code = -1;
            this.count = 0;
        }
        this.data = result;
        this.count = count;
    }

    public Result(T result) {
        if(result == null)
        {
            this.success = false;
            this.code = -1;
        }
        this.data = result;
    }

    public Result(int code, String msg, T result) {
        if(result == null)
        {
            this.code = -1;
            this.success = false;
        }
        else
        {
            this.code = code;
        }
        this.data = result;
        this.msg = msg;
    }


    public Result(int code, String msg) {
        this.code = code;
        if (code==-1){
            this.success=false;
        }
        this.msg = msg;
//        this.success=true;
    }



}
