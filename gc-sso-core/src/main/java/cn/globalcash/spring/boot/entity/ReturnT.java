package cn.globalcash.spring.boot.entity;

import java.io.Serializable;

/**
 * @author: gh
 * @date:2019/6/9
 */
public class ReturnT<T> implements Serializable{
    private static final long serialVersionUID = -5879281033508287492L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final ReturnT<String> SUCCESS = new ReturnT<String>(null);
    public static final ReturnT<String> FAIL = new ReturnT<String>(FAIL_CODE, null);

    private int code;
    private String msg;
    private T data;

    public ReturnT(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
    }

    public ReturnT (int code,String msg) {
        this.code = FAIL_CODE;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
