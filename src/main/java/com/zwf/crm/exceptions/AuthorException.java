package com.zwf.crm.exceptions;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-16 9:51
 */

/**
 * 自定义权限异常
 */
public class AuthorException extends RuntimeException{
    private Integer code=400;
    private String msg="暂无权限！";
    public AuthorException() {
        super("暂无权限！");
    }
    public AuthorException(String msg) {
        super("暂无权限！");
        this.msg=msg;
    }
    public AuthorException(String msg,Integer code) {
        super("暂无权限！");
        this.msg=msg;
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}