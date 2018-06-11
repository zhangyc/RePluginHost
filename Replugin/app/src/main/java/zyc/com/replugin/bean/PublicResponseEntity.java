package zyc.com.replugin.bean;

import java.io.Serializable;

/**
 * 服务器返回消息实体
 */

public class PublicResponseEntity<T> implements Serializable{
    private int code;
    private T   data;
    private String msg;
    private String token;
    private long expire;

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    // 构造函数，初始化code和msg
    public PublicResponseEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 判断结果是否成功
    public boolean isSuccess() {
        return (0 == code || 1 == code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
//        if(true){
//            String result = DBUserUtils.queryCodePairMsg(code);
//            return TextUtils.isEmpty(result) ? msg : result;
//        }
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PublicResponseEntity{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                ", expire=" + expire +
                '}';
    }
}
