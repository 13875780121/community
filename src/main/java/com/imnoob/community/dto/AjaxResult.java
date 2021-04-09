package com.imnoob.community.dto;

import com.imnoob.community.exception.CustomizeException;
import lombok.Data;

@Data
public class AjaxResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public static AjaxResult errorOf(Integer code,String msg){
        AjaxResult<Object> res = new AjaxResult<>();
        res.code = code;
        res.msg = msg;
        return res;
    }

    public static AjaxResult errorOf(CustomizeException e){
        AjaxResult<Object> res = new AjaxResult<>();
        res.code = e.getCode();
        res.msg = e.getMsg();
        return res;
    }



    public static <T> AjaxResult okOf(Integer code,String msg,T data){
        AjaxResult<T> res = new AjaxResult<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }
    public static <T> AjaxResult okOf(Integer code,String msg){
        AjaxResult<T> res = new AjaxResult<>();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }
}
